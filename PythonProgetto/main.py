import sys
from string import ascii_lowercase as alc
from igraph import *
from Levenshtein import distance as lev
from difflib import SequenceMatcher
from random import seed
from random import randint
import difflib

text_file = open("words.italian.txt", "r")
wordsTuple = text_file.read().split()
text_file.close()
words = list(wordsTuple)

#word1 = input('inserisci la prima parola\n')
#word2 = input('inserisci la seconda parola\n')
word1=''
word2=''
while (word2 == '' or word1 == '' or word2 not in words):
    word1 = input('inserisci la prima parola\n')
    word2 = input('inserisci la seconda parola\n')




###############################################################################################R1()#############
def R1(passed):
    indexLetter = 0
    Result = []
    parolaAggiunta = passed
    parolaTolta = passed
    for x in range(len(passed) + 1):
        parolaAggiunta = passed
        for c in alc:
            parolaAggiunta = passed
            parolaAggiunta = parolaAggiunta[:indexLetter] + c + parolaAggiunta[indexLetter:]
            parolaTolta = passed
            parolaTolta = parolaAggiunta[:indexLetter - 1] + parolaAggiunta[indexLetter + 1:]
            # print(parolaTolta)
            for new_word in words:
                if (parolaAggiunta == new_word or parolaTolta == new_word):
                    if (not new_word in Result):
                        Result.append(new_word)
        indexLetter += 1
    return Result

############DOVREBBE ESSERE CORRETTO####################àà
def R2(passed):
    Result = []
    for new_word in words:
        if (len(passed) == len(new_word)):
            sorted_str1 = sorted(passed)
            sorted_str2 = sorted(new_word)
            if (sorted_str1 == sorted_str2):
                Result.append(new_word)
    return Result

############################## R3 ####################àà
def R3(passed):
    indexLetter = 0
    Result = []
    parolaReplace = passed
    for x in range(len(passed) + 1):
        parolaReplace = passed
        for c in alc:
            parolaReplace = passed
            parolaReplace = passed[:indexLetter - 1] + c + passed[indexLetter:]
            for new_word in words:
                if (parolaReplace == new_word):
                    if (not new_word in Result):
                        Result.append(new_word)
        indexLetter += 1
    return Result

def levenshteinDistance(s1, s2):
    if len(s1) > len(s2):
        s1, s2 = s2, s1

    distances = range(len(s1) + 1)
    for i2, c2 in enumerate(s2):
        distances_ = [i2 + 1]
        for i1, c1 in enumerate(s1):
            if c1 == c2:
                distances_.append(distances[i1])
            else:
                distances_.append(1 + min((distances[i1], distances[i1 + 1], distances_[-1])))
        distances = distances_
    return distances[-1]


def levCost(source, dest):
    return lev(source, dest)

def creaCollegamento2(word1, word2):
    piu_vicina = word1
    percorso=[]
    precedent = ""
    cost = sys.maxsize
    percorso.append(word1)
    tmp_piu_vicina=""
    non_trovata=False

    while piu_vicina != word2:
        cost = sys.maxsize
        precedent=piu_vicina
        tmp_piu_vicina = ""#in urne diventa '' x questa riga siccome non trova consigli

        listR1 = R1(piu_vicina)
        if (len(listR1) != 0):
            for el in listR1:
                if (el not in percorso and levCost(el, word2) <= cost):
                    tmp_piu_vicina = el
                    cost = levCost(el, word2)
        listR3 = R3(piu_vicina)
        if (len(listR3) != 0):
            for el in listR3:
                if (el not in percorso and levCost(el, word2) <= cost):#< o <????
                    tmp_piu_vicina = el
                    cost = levCost(el, word2)
        listR2 = R2(piu_vicina)
        if len(listR2)!=0:#con levesthein + costosa
            for el in listR2:
                if (el not in percorso and levCost(el, word2) < cost):
                    tmp_piu_vicina=el
                    cost = levCost(el, word2)

        if (tmp_piu_vicina != precedent and tmp_piu_vicina != ''):
            piu_vicina = tmp_piu_vicina
            percorso.append(piu_vicina)
        if(tmp_piu_vicina==''):
            non_trovata=True
            break


    if(non_trovata):
        print("non esiste un percorso per arrivare dalla sorgente alla destinazione")
    return percorso

print(creaCollegamento2(word1,word2))
