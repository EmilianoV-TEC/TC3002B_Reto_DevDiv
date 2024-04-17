#File that moves the desire percentage of files from a folder to another
#In this case moving 20% to the Test folder ad keeping 80% for Training
#This file was made and tested in Google Colab

import os
import random
import shutil
import csv

def move_random_files(source_folder, destination_folder, percentage):
    # Ensure the source folder exists
    if not os.path.exists(source_folder):
        print("Source folder does not exist.")
        return

    # Ensure the destination folder exists or create it
    if not os.path.exists(destination_folder):
        os.makedirs(destination_folder)

    # Get a list of files in the source folder
    files = os.listdir(source_folder)

    # Calculate the number of files to move based on the percentage
    num_files_to_move = int(len(files) * percentage)

    # Choose randomly files to move
    files_to_move = random.sample(files, num_files_to_move)

    # Move the selected files to the destination folder
    for file_name in files_to_move:
        source_path = os.path.join(source_folder, file_name)
        destination_path = os.path.join(destination_folder, file_name)
        shutil.move(source_path, destination_path)
        print(f"Moved {file_name} to {destination_folder}")



#source_folder = "C:\\Users\\jorge\\Documents\\tc3002b_reto\\conplag_version_2\\versions\\version_1\\{}"
#destination_folder = "C:\\Users\\jorge\\Documents\\tc3002b_reto\\conplag_version_2\\versions\\version_1\\Test\\{}"
percentage = 1.0  # 20%
#move_random_files(source_folder, destination_folder, percentage)

# Open the CSV file in read mode
with open('C:\\Users\\jorge\\Documents\\tc3002b_reto\\conplag_version_2\\versions\\train_pairs.csv', 'r') as csv_file:
    csv_reader = csv.reader(csv_file)
    for row in csv_reader:
        print(row[0])
        source_folder = "C:\\Users\\jorge\\Documents\\tc3002b_reto\\conplag_version_2\\versions\\version_1\\"+row[0]
        destination_folder = "C:\\Users\\jorge\\Documents\\tc3002b_reto\\conplag_version_2\\versions\\version_1\\Train\\"+row[0]
        move_random_files(source_folder, destination_folder, percentage)

