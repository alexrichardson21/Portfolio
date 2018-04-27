# Airplane Game
# by: Alex Richardson
# date: 10/10/2012
##########################################################

from graphics import *
makeGraphicsWindow(1000,800)

##########################################################
# you can set some global constants here

GROUND_HEIGHT = 50
AIR_HEIGHT = getWindowHeight() - GROUND_HEIGHT
GRAVITY = 3
AIRPLANE_IMAGE = loadImage("cartoon-biplane.png", scale=.25)
# http://www.free4teachers.co.uk/primary/free-cartoon-plane-images
BACKROUND = loadImage("Sky2.jpg")
#http://www.allabouthappylife.com/wallpaper/sky/blue-sky-dsc03155-wp.jpg
MONSTER = loadImage("Monster.png", scale=.15)
#http://www.clker.com/cliparts/2/2/0/a/12387004981877427873StudioFibonacci_Cartoon_monsters_4.svg.hi.png
EXPLOSION = loadImage("explosion.gif")
GAME_TITLE = setWindowTitle("Don't Crash That Plane")
###########################################################
# this function is called once to initialize your new world

def startWorld(world):
    world.planeX = -30
    world.planeY = 35
    world.monsterX = .5 * getWindowWidth()
    world.monsterY = 100
    world.monsterThick = getImageWidth(MONSTER) / 2
    world.monsterYD = 3
    world.waterRatio = 1
    world.landX = getWindowWidth()/world.waterRatio
    world.landY = AIR_HEIGHT
    world.landSize = getWindowWidth()-world.landX
    world.waterX = 0
    world.waterY = AIR_HEIGHT
    world.waterSize = getWindowWidth()-world.landSize
    world.score = 0
    world.gameOver = False
    world.lose = False
    world.planeTip = 0
    world.planeWheel = 0
    
    setBackground(BACKROUND)

###########################################################
# this function is called every frame to update your world

def updateWorld(world):
    if world.gameOver == False and world.lose == False:
        if isKeyPressed("up"):
            world.planeY = world.planeY - 4
        if isKeyPressed("down"):
            world.planeY = world.planeY + 4
        if world.planeWheel >= world.landY and world.planeX >= world.landX and world.planeX <= world.landX+world.landSize:
            world.score = world.score+1
            world.planeY = 35
            world.planeX = -30
            if world.waterRatio >= .25:
                world.waterRatio = world.waterRatio * .75
            else:
                world.waterRatio = world.waterRatio * .95
        if world.planeWheel >= world.waterY and world.planeX >= world.waterX and world.planeX <= world.waterX+world.waterSize:
            world.lose = True
        if world.planeX - getImageWidth(AIRPLANE_IMAGE)/2 > getWindowWidth():
            world.lose = True
        if math.sqrt ((world.planeTip - world.monsterX)**2 + (world.planeY - world.monsterY)**2) <= world.monsterThick or math.sqrt ((world.planeX - world.monsterX)**2 + (world.planeY - world.monsterY)**2) <= world.monsterThick:
            world.lose = True
        if isKeyPressed("r"):
            startWorld(world)
        if world.monsterY <= 50:
            world.monsterYD = -1 * (world.monsterYD) + .25
        if world.monsterY >= getWindowHeight() - 50 - GROUND_HEIGHT:
            world.monsterYD = -1 * (world.monsterYD) - .25
        world.planeX = world.planeX + 3
        world.monsterY = world.monsterY + world.monsterYD
        world.landX = getWindowWidth()/(1+world.waterRatio)
        world.landSize = getWindowWidth()-world.landX
        world.waterSize = getWindowWidth()-world.landSize
        world.planeTip = (getImageWidth(AIRPLANE_IMAGE)/2)+world.planeX
        world.planeWheel = (getImageHeight(AIRPLANE_IMAGE)/2)+world.planeY
    if world.lose == True:
        if isKeyPressed("r"):
            startWorld(world)
    if world.gameOver == True:
        startWorld(world)
    ###########################################################
# this function is called every frame to draw your world

def drawWorld(world):
    drawImage(AIRPLANE_IMAGE,world.planeX,world.planeY)
    drawImage(MONSTER, world.monsterX, world.monsterY)
    fillRectangle(world.waterX, world.waterY, world.waterSize, GROUND_HEIGHT, "blue")
    fillRectangle(world.landX, world.landY, world.landSize, GROUND_HEIGHT, "green")
    drawString("score:", getWindowWidth() -200, 20)
    drawString(world.score, getWindowWidth() -100, 20)
    if world.lose == True:
        drawImage(EXPLOSION, world.planeX, world.planeY)
        drawString("Game Over", 450,400)
        drawString("Press R to restart", 418,450, italic=True, color = "grey")
###########################################################
# run the animation

runGraphics(startWorld, updateWorld, drawWorld)
