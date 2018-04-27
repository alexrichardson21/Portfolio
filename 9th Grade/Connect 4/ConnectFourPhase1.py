# Connect 4
# by: Alex Richardson
from graphics import *
makeGraphicsWindow(700, 600)
DARKCHECKER = loadImage("darkChecker.jpg", scale = 1)
LIGHTCHECKER = loadImage("lightChecker.jpg")
############################################################
# this function is called once to initialize your new world
def startWorld(world):
    onKeyPress(myKeyPressedListener)
    onMousePress(myMousePressedListener)
    #onMouseMotion(myMouseMovementListener)
    world.turn = "black"
    world.turns = []
    world.columnHeight = [0,0,0,0,0,0,0]
    world.checkers = []
    world.redWin = False
    world.blackWin = False
    world.board = []
    world.xVals = range(50,751,100)
    world.yVals = range(50,651,100)
    world.columnsWidth = getWindowWidth() / 7
    world.rowsHeight = getWindowHeight() / 6
    world.backColor = "lawngreen"
    setBackground(world.backColor)
    for x in world.xVals:
        for y in world.yVals:
            world.board.append((x,y))
############################################################
# this function is called once every time the mouse is clicked
def myKeyPressedListener(world, key):
    if sameKeys(key, "r"):
        startWorld(world)
    if sameKeys(key, "c"):
        if world.backColor == "yellow":
            world.backColor = "lawngreen"
        elif world.backColor == "lawngreen":
            world.backColor = "white"
        else:
            world.backColor = "yellow"
############################################################
# this function is called once every time the mouse is clicked
def myMousePressedListener(world, mouseX, mouseY, button):
    columnNum = mouseX / world.columnsWidth
    if world.columnHeight[columnNum] < 6:
        world.checkers.append((columnNum,world.columnHeight[columnNum], world.turn))
        world.columnHeight[columnNum] += 1
        world.turns.append(1)
        if len(world.turns) % 2 == 0:
            world.turn = "black" 
        elif len(world.turns) % 2 == 1:
            world.turn = "red"
    #print world.columnHeight

#def myMouseMovementListener(world,mouseX,mouseY,mouseXChange,mouseYChange,):
    #if world.turn == "red":
        #world.checkers.append((columnNum,world.columnHeight[columnNum], world.turn))
        #pass
    #if world.turn == "black":
        #world.checkers.append((columnNum,world.columnHeight[columnNum], world.turn))
        #pass
def winFinder(checkers,colorToCheck):
    pass
def winFinderHori(checkers,colorToCheck):
    for headRow in range(6):
        for headColumn in range(4):
            pass
def checkHorizontalLine(checkers,colorToCheck,headRow,headColumn):
    if (headRow,headColumn,colorToCheck) in checkers:
        pass

############################################################
# this function is called every frame to update your world
def updateWorld(world):
    setBackground(world.backColor)
############################################################
# this function is called every frame to draw your world
def drawWorld(world):
    
    if world.blackWin == True:
        drawString("Black Wins", 200, .5 * getWindowHeight())
    if world.redWin == True:
        drawString("Red Wins", 200, .5 * getWindowHeight(), color = "red")
    for (x,y) in world.board:
        fillCircle(x, y, 35, color = "white")
        drawCircle(x, y, 35)
    for (row,column,color) in world.checkers:
        fillCircle(world.columnsWidth / 2 + row * world.columnsWidth, getWindowHeight()- world.rowsHeight / 2 - column * world.columnsWidth, 35, color)
    if world.turn == "red":
        drawString("Red's Turn", 605, 2, color = "red", size = 20)
    if world.turn == "black":
        drawString("Black's Turn", 20, 2, color = "black", size = 20) 
############################################################
runGraphics(startWorld, updateWorld, drawWorld)

