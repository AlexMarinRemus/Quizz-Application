# Starting template

This README will need to contain a description of your project, how to run it, how to set up the development environment, and who worked on it.
This information can be added throughout the course, except for the names of the group members.
Add your own name (do not add the names for others!) to the section below.

## Description of project

The Quizzzz Game is easy to play! The goal is to get as many points as you can by answering 20 energy-consumption based questions. You can use jokers to double your points, eliminate wrong answers, and, in multiplayer mode, to decrease time for other players. You have a choice of single player or multiplayer mode. In single player, you play to make it to the top of the global leaderboard. In multiplayer you can play with up to 10 people, use emojis to show how you're feeling.


## How to run it

Pulling from this repository and running the code should be sufficient for running the game. To run the code, first run the Spring server, and then run the client side code. Currently, a significantly abbreviated version of the activity bank entries/images are in the gitlab. The files that enable the full activity repository to be configured into the database (and used to generate questions/answers) are not in this gitlab repository due to their size (to get the full activity bank files for this project please contact one of the contributors).

To add a record into the activity bank to be used to generate questions, the json entry must be formatted as follows:

{
    "consumption_in_wh": XXXX,
    "image_path": "images/XX/XXXX.png",
    "title": "Lorem Ipsum",
    "title_id": "Lorem Ipsum",
    "source": "https://www.website.com",
    "_class": "commons.Activity"
}

where the consumption_in_wh is an integer, the image path leads to a corresponding image for the entry.

## How to contribute to it

To contribute, contact the contributor of this project.
