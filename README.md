# Mines3D
Minesweeper on n\*m\*2 matrix for Android. The goal of the game is find the all of mines and mark them by long press. This is probably impossible without dicovering empty fields. When you double-tap on field without a mine[x, y, z], you shall get the number of mines in fields (if exists) [(x-1), y, z], [(x+1), y, z], [x, (y-1), z], [x, (y+1), z], [x, y, (z+1) mod 1]. If you double-tapped on field with mine, the game would ended.

There are 5 modes with possible extension. 
  1) 5x5x2 with 7 mines
  2) 8x8x2 with 16 mines
  3) 10x10x2 with 25 mines
  4) 12x12x2 with 50 mines
  5) 15x15x2 with 90 mines

## Google Play
The application is published in Google Play. After any significant change of the project is rebuild and updated.
https://play.google.com/store/apps/details?id=cos.premy.mines&hl=en

## Getting Started
### Prerequisites
* Android Studio 3.0
* Device with Android newer then 4.4
            
### Installing
Download and install Android Studio 3.0:
https://dl.google.com/dl/android/studio/install/3.0.0.18/android-studio-ide-171.4408382-windows.exe 

Then install a virtual Android machine, optimally with version 7. You can use a real device too, but there must be installed Android newer then 4.4. A widescreen is required.

### Building and running
Download repository and open root folder as project in Android Studio. Do Grandle sync and clean'n'build. After that, you can try to run it on your device.

## User documentation
There are 2 files:
* Uživatelská dokumentace.docx
* Uživatelská dokumentace.pdf

It's written in Czech and it is rather pies of garbage than serious documentation. There are no ambicions to write a better one, because a user himself should understand the application. (one of tasks of game)

## License
This project is licensed under the GPL-3.0 License - see the [LICENSE.md](LICENSE.md) file for details

## Contributing
In very improbable case you wanted contribute anything in this code, it should be writted in a rational way and it should work.

## Code description
### Activities
The MainActivity is menu, which is shown first. The GameActivity contains a view, where the game is played.
### Static class - LoadedGame
To prevent bugs coming from Android tendencies of endless reinicializations of Activity classes, the game data were moved to static class LoadedGame. There are no limitations coming from that, because only one game can be played in the same time.

There are saved 3 instances of 3 classes: GameStatus, MinesContainer, Activity (the last MainActivity loaded)
### Package - cos.premy.mines
You can find here the elemetal classes of the program.
 * LoadedGame - ...
 * MyHappyException - Exception class for throwing exceptions specific for this program
 * Utils - Any generaly useful code
 * MainActivity - Activity class, which defines modes of game
 * 

## Author
Přemysl Šťastný
