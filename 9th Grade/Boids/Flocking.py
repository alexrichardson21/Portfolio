# Flocking
# Alex Richardson
###############################
from graphics import *
import random, math
makeGraphicsWindow(800,800, True)
###############################
# Them means other vector
class Vector:
    def __init__(self,x,y):
        self.x = x
        self.y = y
        
    def length(self):
        length = math.sqrt (self.x**2 + self.y**2)
        return length
    
    def add(self, them):
        self.x += them.x
        self.y += them.y
        
    def subtract(self, them):
        self.x -= them.x
        self.y -= them.y
        
    def multiply(self,number):
        self.x *= number
        self.y *= number
        
    def divide(self,number):
        if number!=0:
            self.x /= number
            self.y /= number
        
    def normalize(self):
        self.divide(self.length())

###############################
# Them is a Point object
class Point:
    def __init__(self,x,y):
        self.x = x
        self.y = y
        
    def distance(self,them):
        xdiff = self.x - them.x
        ydiff = self.y - them.y
        dist = math.sqrt(xdiff**2 + ydiff**2)
        return dist
    
    def move(self, vector):
        self.x += vector.x
        self.y += vector.y
        
    def makeVectorTo(self, them):
        return Vector (them.x - self.x, them.y - self.y)
    
###############################
class Boid:
    def __init__(self):
        
        startSpeeds = [-1,-0.5,0.5,1]
        self.location = Point(random.uniform(0,getWindowWidth()),random.uniform(0,getWindowHeight()))
        self.velocity = Vector(random.choice(startSpeeds),random.choice(startSpeeds))
        self.color = (random.randint(50,250),random.randint(50,250),random.randint(200,250))
    def update(self, world):
        self.separation(world)
        self.alignment(world)
        #Border
        if self.location.x < 70:
            self.velocity.add(Vector(1.5,0))
        if self.location.x > getWindowWidth() - 70:
            self.velocity.add(Vector(-1.5,0))
        if self.location.y < 70:
            self.velocity.add(Vector(0,1.5))
        if self.location.y > getWindowHeight() - 70:
            self.velocity.add(Vector(0,-1.5))
        #Speed
        if self.velocity.length() > 6:
            self.velocity.normalize()
            self.velocity.multiply(6)

        self.location.move(self.velocity)
    def separation(self, world):
        for boid in world.boids:
            if boid != self:
                if self.location.distance(boid.location)<25:
                    separationVector=boid.location.makeVectorTo(self.location)
                    if self.location.distance(boid.location)!= 0:
                        separationVector.divide((self.location.distance(boid.location)**2))
                        separationVector.multiply(15)
                        self.velocity.add(separationVector)
    def alignment(self, world):
        alignmentVector=Vector(0,0)
        count = 0
        for boid in world.boids:
            if boid!=self:
                distanceToBoid=self.location.distance(boid.location)
                if distanceToBoid<40:
                    alignmentVector.add(boid.velocity)
                    count+=1
        alignmentVector.divide(count)
        alignmentVector.multiply(.5)
        self.velocity.add(alignmentVector)
    def cohesion(self, world):
        cohesionScale = .05
        xVals = 0
        yVals = 0
        count = 0
        for boid in world.boids:
            if self.location.distance(them.location) < 40:
                xVals += them.location.x
                yVals += them.location.y
                count += 1
        xVals /= count
        yVals /= count
        cohesionPoint = Point(xVals, yVals)
        cohesionVector = Vector(cohesionPoint)
        cohesionVector.multiply(cohesionScale)
        self.velocity.add(cohesionVector)
    
    def draw(self):
        fillCircle(self.location.x,self.location.y,10,self.color)
        #line = self.velocity
        #line.normalize()
        drawLine(self.location.x,self.location.y,self.velocity.x + 1 * self.location.x ,self.velocity.y + 1 * self.location.y, self.color)


###############################
def startWorld(world):
    world.color=getColorsList()
    world.boids = []
    for spawn in range(0,50):
        world.boids.append(Boid())

def updateWorld(world):
    onKeyPress(myKeyPressedListener)
    for boid in world.boids:
        boid.update(world)

def myKeyPressedListener(world,key):
    if sameKeys(key, "f"):
        pass
        

def drawWorld(world):
    for boid in world.boids:
        boid.draw()

################################################
runGraphics(startWorld, updateWorld, drawWorld)
