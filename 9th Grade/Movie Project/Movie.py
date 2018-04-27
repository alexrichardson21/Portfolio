# Movie Project - Alex Richardson
##################################
actorDict = dict() #key-actor #value-number movies they are in
directorDict = dict()
moviesfile = open('moviesdata.txt', 'r')



for line in moviesfile:
    line = line.strip()
    (title, year, directors, actors, genre) = line.split('|')
################################################################
    listOfActors = actors.split(',')
    for actor in listOfActors:
        actor = actor.strip()
        if actor is not '':
            if actor in actorDict:
                actorDict[actor] += 1
            else:
                actorDict[actor] = 1
################################################
    listOfDirectors = directors.split(',')
    for director in listOfDirectors:
        director = director.strip()
        if director is not '':
            if director in directorDict:
                directorDict[director] += 1
            else:
                directorDict[director] = 1
#####################################################
#bestActor = 0
#bestActorMovie = 0
#for actor in actorDict:
#    if actorDict[actor] > bestActorMovie:
#       bestActorMovie = actorDict[actor]
#       bestActor = actor
#moviesfile.close()
#########################################################
def numMovies(actorname):
    return actorDict[actorname]

actorList = actorDict.keys()
actorList.sort(key=numMovies, reverse= True)
for num in range(50):
    print actorList[num]

Actor = 'Nicolas Cage'
Director = 'Steven Spielberg'
print Actor, "is in", actorDict[Actor], "movie(s)"
print Director, "directed", directorDict[Director], "movie(s)"
#print bestActor
#print bestActorMovie
