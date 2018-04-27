# Connect 4
# by: Alex Richardson
from graphics import *
makeGraphicsWindow(700, 600)
setWindowTitle("Connect 4")
############################################################
# this function is called once to initialize your new world
def startWorld(world):
    onKeyPress(myKeyPressedListener)
    onMousePress(myMousePressedListener)
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
        if world.backColor == "khaki":
            world.backColor = "lawngreen"
        elif world.backColor == "lawngreen":
            world.backColor = "white"
        elif world.backColor == "white":
            world.backColor = "lightblue"
        elif world.backColor == "lightblue":
            world.backColor = "khaki"
############################################################
# this function is called once every time the mouse is clicked
def myMousePressedListener(world, mouseX, mouseY, button):
    if world.redWin == False and world.blackWin == False:
        columnNum = mouseX / world.columnsWidth
        if world.columnHeight[columnNum] < 6:
            world.checkers.append((world.columnHeight[columnNum], columnNum, world.turn))
            world.columnHeight[columnNum] += 1
            if winFinder(world.checkers, world.turn) == True:
                if world.turn == "black":
                    world.blackWin = True
                elif world.turn == "red":
                    world.redWin = True
            else: 
                world.turns.append(1)
                if len(world.turns) % 2 == 0:
                    world.turn = "black" 
                elif len(world.turns) % 2 == 1:
                    world.turn = "red"
            #print world.columnHeight

###############################################################
def winFinder(checkers,colorToCheck):
    if winFinderHori(checkers,colorToCheck) == True:
        return True
    if winFinderVert(checkers,colorToCheck) == True:
        return True
    if winFinderDiag(checkers,colorToCheck) == True:
        return True
def winFinderHori(checkers,colorToCheck):
    for headRow in range(6):
        for headColumn in range(4):
            if checkHoriLine(checkers,colorToCheck,headRow,headColumn) == True:
                return True
def winFinderVert(checkers,colorToCheck):
    for headRow in range(6):
        for headColumn in range(7):
            if checkVertLine(checkers,colorToCheck,headRow,headColumn) == True:
                return True

def winFinderDiag(checkers,colorToCheck):
    for headRow in range(6):
        for headColumn in range(7):
            if checkDiagLineR(checkers,colorToCheck,headRow,headColumn) == True:
                return True
            if checkDiagLineL(checkers,colorToCheck,headRow,headColumn) == True:
                return True
            
def checkHoriLine(checkers,colorToCheck,headRow,headColumn):
    if (headRow,headColumn,colorToCheck) in checkers:
        if (headRow, headColumn + 1,colorToCheck) in checkers:
            if (headRow, headColumn + 2,colorToCheck) in checkers:
                if (headRow, headColumn + 3,colorToCheck) in checkers:
                    return True
def checkVertLine(checkers,colorToCheck,headRow,headColumn):
    if (headRow,headColumn,colorToCheck) in checkers:
        if (headRow + 1, headColumn,colorToCheck) in checkers:
            if (headRow + 2, headColumn,colorToCheck) in checkers:
                if (headRow + 3, headColumn,colorToCheck) in checkers:
                    return True
def checkDiagLineR(checkers,colorToCheck,headRow,headColumn):
    if (headRow,headColumn,colorToCheck) in checkers:
        if (headRow + 1, headColumn + 1,colorToCheck) in checkers:
            if (headRow + 2, headColumn + 2,colorToCheck) in checkers:
                if (headRow + 3, headColumn + 3,colorToCheck) in checkers:
                    return True
def checkDiagLineL(checkers,colorToCheck,headRow,headColumn):
    if (headRow,headColumn,colorToCheck) in checkers:
        if (headRow - 1, headColumn + 1,colorToCheck) in checkers:
            if (headRow - 2, headColumn + 2,colorToCheck) in checkers:
                if (headRow - 3, headColumn + 3,colorToCheck) in checkers:
                    return True

############################################################
# this function is called every frame to update your world
def updateWorld(world):
    setBackground(world.backColor)
############################################################
# this function is called every frame to draw your world
def drawWorld(world):
    for (x,y) in world.board:
        fillCircle(x, y, 35, color = "white")
        drawCircle(x, y, 35)
    for (column,row,color) in world.checkers:
        fillCircle(world.columnsWidth / 2 + row * world.columnsWidth, getWindowHeight()- world.rowsHeight / 2 - column * world.columnsWidth, 35, color)
    if world.turn == "red":
        drawString("Red's Turn", 605, 2, color = "red", size = 20)
    if world.turn == "black":
        drawString("Black's Turn", 20, 2, color = "black", size = 20)
    if world.blackWin == True:
        fillRectangle(200,200,300,200,"white")
        drawRectangle(200,200,300,200)
        drawString("Black Wins", 300, 250)
        drawString("Press r to Restart", 280, 320, 25, "grey", italic = True) 
    if world.redWin == True:
        fillRectangle(200,200, 300, 200,"white")
        drawRectangle(200,200,300,200)
        drawString("Red Wins", 300, 250, color = "red")
        drawString("Press r to Restart", 280, 320, 25, "grey", italic = True)
############################################################
runGraphics(startWorld, updateWorld, drawWorld)

