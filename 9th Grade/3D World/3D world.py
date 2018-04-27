# 3D World - Alex Richardson
##############################################
from graphics3d import *
makeGraphicsWindow(800, 600, True)
############################


class Wall:
    def __init__(self,x,y,z):
        self.x = x
        self.y = y
        self.z = z
        self.model = Box3D(2,3,2,texture = "hedge.jpg")
    def draw(self,world):
        draw3D(self.model,self.x,self.y,self.z)
class Floor:
    def __init__(self,x,y,z):
        self.x = x
        self.y = y - 1
        self.z = z
        self.model = Rect3D(2,2, texture = 'wood.jpg')
    def draw(self,world):
        draw3D(self.model,self.x,self.y,self.z, anglex = 90)
class Donut:
    def __init__(self,x,y,z):
        self.x = x
        self.y = y
        self.z = z
        self.anglex = 0
        self.angley = 0
        self.model = Torus3D(.2, .15, texture = 'cake.jpg')
    def update(self,world):
        self.anglex += 1
        self.angley += 1
    def draw(self,world):
        draw3D(self.model,self.x,self.y,self.z,self.anglex,self.angley)
class HUD:
    def __init__(self,world):
        self.healthWidth = getWindowWidth()
        self.healthHeight = getWindowHeight() * .03 
        self.healthCanvas = Canvas2D(getWindowWidth(), self.healthHeight, 0.8, 5)
        self.healthColor = "green"

        self.donutNum = len(world.donuts)
        self.donutCanvas = Canvas2D(100,50,1.0,2)

        self.circleCanvas = Canvas2D(100,100,.6,.1)

        self.winCanvas = Canvas2D(400, 200)
        self.loseCanvas = Canvas2D(400,200)
    def update(self, world):
        self.donutNum = len(world.donuts)
        self.healthWidth = world.speed * 5 * getWindowWidth()
    def draw(self,world):
        clearCanvas2D(self.donutCanvas)
        clearCanvas2D(self.healthCanvas)
        clearCanvas2D(self.circleCanvas)
        #################################
        drawString2D(self.donutCanvas,self.donutNum,10,10)
        fillRectangle2D(self.healthCanvas, 0, 0, self.healthWidth, self.healthHeight, self.healthColor)
        fillCircle2D(self.circleCanvas, -5,-5,80,"grey")
        ################################################
        draw2D(self.healthCanvas,0,getWindowHeight()*.97)
        draw2D(self.circleCanvas,-5,-5)
        draw2D(self.donutCanvas,10,10)
        if world.lose == True:
            clearCanvas2D(self.loseCanvas)
            drawString2D(self.loseCanvas, "Game Over", 160, 150, size=40, color="white")
            draw2D(self.loseCanvas, 160, 150)
        if world.win == True:
            clearCanvas2D(self.winCanvas)
            drawString2D(self.winCanvas, "You Win", 170, 150, size=40, color="white")
            draw2D(self.winCanvas, 170, 150)
############################
def loading(world):
    world.walls = []
    world.floors = []
    world.donuts = []
    file3d = open('World.txt', 'r')
    x = 0
    y = 0
    z = 0
    for line in file3d:
        x = 0
        z += 2
        for letter in line:
            x += 2
            if letter is '#':
                world.walls.append(Wall(x,y,z))
            if letter is 'p':
                setCameraPosition(x, y + .5, z)
            if letter is 'd':
                world.donuts.append(Donut(x,y,z))
            if letter is '.' or 'p' or 'd':
                world.floors.append(Floor(x,y,z))
    file3d.close()
#############################
def collision(world,objList,camX,camY,camZ,newCamX,newCamY,newCamZ):
    safeMove = True
    safeMoveX = True
    safeMoveZ = True
    for wall in objList:
        if wall.x + 1.2 >= newCamX and wall.x - 1.2 <= newCamX and wall.z + 1.2 >= newCamZ and wall.z - 1.2 <= newCamZ:
            safeMove = False
        if wall.x + 1.2 >= newCamX and wall.x - 1.2 <= newCamX and wall.z + 1.2 >= camZ and wall.z - 1.2 <= camZ:
            safeMoveX = False
        if wall.x + 1.2 >= camX and wall.x - 1.2 <= camX and wall.z + 1.2 >= newCamZ and wall.z - 1.2 <= newCamZ:
            safeMoveZ = False
    if safeMove == False and safeMoveX == False and safeMoveZ == False:
        setCameraPosition(camX,camY,camZ)
    if safeMove == False and  safeMoveX == True and safeMoveZ == False:
        setCameraPosition(newCamX,camY,camZ)
    if safeMove == False and safeMoveX == False and safeMoveZ == True:
        setCameraPosition(camX,camY,newCamZ)
    if safeMove == False and  safeMoveX == True and safeMoveZ == True:
        setCameraPosition(camX,camY,camZ)
def movement(world):
    (camX,camY,camZ) = getCameraPosition()
    if isKeyPressed("w"):
        moveCameraForward(world.speed)
    if isKeyPressed("s"):
        moveCameraBackward(world.speed)
    if isKeyPressed("a"):
        strafeCameraLeft(.5 * world.speed)
    if isKeyPressed("d"):
        strafeCameraRight(.5 * world.speed)
    if isKeyPressed("left"):
        adjustCameraRotation(2, 0, 0)
    if isKeyPressed("right"):
        adjustCameraRotation(-2, 0, 0)
    #if isKeyPressed("up"):
        #adjustCameraRotation(0, -2, 0)
    #if isKeyPressed("down"):
        #adjustCameraRotation(0, 2, 0)
    (newCamX,newCamY,newCamZ) = getCameraPosition()

    collision(world,world.walls,camX,camY,camZ,newCamX,newCamY,newCamZ)


#####################################

    for donut in world.donuts:
        if donut.x + 1.2 >= newCamX and donut.x - 1.2 <= newCamX and donut.z + 1.2 >= newCamZ and donut.z - 1.2 <= newCamZ:
            world.donuts.remove(donut)
            if world.speed <= .1:
                world.speed += .1
            else:
                world.speed = .2

############################

def startWorld(world):
    world.lose = False
    world.win = False
    loading(world)
    world.sky = Sphere3D(40, 24, texture="clouds.jpg")
    world.angle = 0.0
    world.speed = .1
    setAmbientLight(.8)
    world.hud = HUD(world)

def updateWorld(world):
    if world.speed <= 0:
        world.lose = True
    if len(world.donuts) <= 0:
        world.win = True
    if world.win == False and world.lose == False:
        movement(world)
        world.angle += .02
        world.speed -= .0001
        for donut in world.donuts:
            donut.update(world)
    if isKeyPressed("r"):
        startWorld(world)
    world.hud.update(world)

def drawWorld(world):
    (camHeading, camPitch, camRoll) = getCameraRotation()
    for wall in world.walls:
        wall.draw(world)
    for floor in world.floors:
        floor.draw(world)
    for donut in world.donuts:
        donut.draw(world)
    draw3D(world.sky, 10, 0, 10, 0, world.angle, scale = 1)
    world.hud.draw(world)

runGraphics(startWorld, updateWorld, drawWorld)

