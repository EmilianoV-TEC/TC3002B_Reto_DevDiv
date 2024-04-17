import os
import random
import shutil

def move_files(name1, name2, source_folder, destination_folder):
    # Ensure the source folder exists
    if not os.path.exists(source_folder):
        print("Source folder does not exist.")
        return

    # Ensure the destination folder exists or create it
    if not os.path.exists(destination_folder):
        os.makedirs(destination_folder)

    # Get a list of files in the source folder
    files = os.listdir(source_folder)

    # Move the selected files to the destination folder
    source_path = os.path.join(source_folder, name1)
    destination_path = os.path.join(destination_folder, name1)
    shutil.copy(source_path, destination_path)

    source_path = os.path.join(source_folder, name2)
    destination_path = os.path.join(destination_folder, name2)
    shutil.copy(source_path, destination_path)

file = open('pairs.txt','r')
source_folder = "/Users/andresacevedo/Downloads/fire14-source-code-training-dataset/java"

while True:
    content = file.readline()
    if not content:
        break
    name1 = ""
    name2 = ""
    flag = 0
    for contents in content:
        if contents != " ":
            if flag == 0:
                name1 += contents
            else:
                name2 += contents
        else: 
            flag = 1
    name2 = name2[:-1]
    destination_folder = "/Users/andresacevedo/Downloads/prueba/"+name1 + "-" + name2
    move_files(name1, name2, source_folder, destination_folder)