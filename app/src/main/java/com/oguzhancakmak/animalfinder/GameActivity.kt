package com.oguzhancakmak.animalfinder

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val animalList: ArrayList<Animal> = setList()

        var point = 1
        var level = 1
        val gameList = gameMaker(animalList, level)
        var correctAnimal:Animal = gameList[(0 until gameList.size).random()]
        var mediaPlayer = MediaPlayer.create(this, correctAnimal.sound)
        mediaPlayer.start()

        gameRecycleView.apply{
            layoutManager = GridLayoutManager(this@GameActivity, 2)
            adapter = GameAdapter(gameList, object : GameAdapter.OnClickListenerGame {

                override fun onItemClick(currentAnimal:Animal) {

                    // GAME FINISH
                    if(point == 15) {
                        mediaPlayer.stop()
                        mediaPlayer = MediaPlayer.create(this@GameActivity, R.raw.correct)
                        mediaPlayer.start()

                        val sharedPreferences = this@GameActivity.getSharedPreferences(packageName,MODE_PRIVATE)
                        sharedPreferences.edit().putInt("score", 15).apply()

                        val intent = Intent(this@GameActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else {

                        // CORRECT ANSWER
                        if (currentAnimal.name == correctAnimal.name) {
                            mediaPlayer.stop()
                            mediaPlayer = MediaPlayer.create(this@GameActivity, R.raw.correct)
                            mediaPlayer.start()

                            if (point % 3 == 0 && point != 15)
                                level++
                            gameList.clear()
                            gameList.addAll(gameMaker(animalList, level))
                            adapter?.notifyDataSetChanged()
                            point++

                            currentScoreText.text = "Score: " + (point - 1)
                            levelText.text = "Level: " + level

                            correctAnimal = gameList[(0 until gameList.size).random()]
                            mediaPlayer = MediaPlayer.create(this@GameActivity, correctAnimal.sound)
                            mediaPlayer.start()
                        }
                        // FALSE ANSWER
                        else {
                            mediaPlayer.stop()
                            mediaPlayer = MediaPlayer.create(this@GameActivity, R.raw.wrong)
                            mediaPlayer.start()

                            // Highest Score
                            val sharedPreferences =
                                this@GameActivity.getSharedPreferences(packageName, MODE_PRIVATE)
                            val highestScore: Int = sharedPreferences.getInt("score", 0)
                            if (point > highestScore) {
                                sharedPreferences.edit().putInt("score", point - 1).apply()
                            }

                            val intent = Intent(this@GameActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            })
        }
    }

    private fun gameMaker(animalList: ArrayList<Animal>, level: Int): ArrayList<Animal> {
        val randomNumbers: ArrayList<Int> = randomizer(level)
        val newAnimalList: ArrayList<Animal> = ArrayList()

        if (level == 1) {
            for (i in 0..1)
                newAnimalList.add(animalList[randomNumbers[i]])
        } else if (level == 2) {
            for (i in 0..2)
                newAnimalList.add(animalList[randomNumbers[i]])
        } else if (level == 3) {
            for (i in 0..3)
                newAnimalList.add(animalList[randomNumbers[i]])
        } else if (level == 4) {
            for (i in 0..5)
                newAnimalList.add(animalList[randomNumbers[i]])
        } else if (level == 5) {
            for (i in 0..7)
                newAnimalList.add(animalList[randomNumbers[i]])
        }

        return newAnimalList
    }

    private fun randomizer(level: Int): ArrayList<Int> {
        var randomNumberArray: ArrayList<Int> = ArrayList()
        val s: MutableSet<Int> = mutableSetOf()

        if (level == 1) {
            while (s.size < 2) { s.add((0..20).random()) }

        } else if (level == 2) {
            while (s.size < 3) { s.add((0..20).random()) }

        } else if (level == 3) {
            while (s.size < 4) { s.add((0..20).random()) }

        } else if (level == 4) {
            while (s.size < 6) { s.add((0..20).random()) }

        } else if (level == 5) {
            while (s.size < 8) { s.add((0..20).random()) }
        }
        randomNumberArray = s.toList() as ArrayList<Int>

        return randomNumberArray
    }

    private fun setList(): ArrayList<Animal> {
        return arrayListOf(
            Animal("bee", R.drawable.ic_bee, R.raw.bee),
            Animal("cat", R.drawable.ic_cat, R.raw.cat),
            Animal("chicken", R.drawable.ic_chicken, R.raw.chicken),
            Animal("cow", R.drawable.ic_cow, R.raw.cow),
            Animal("dog", R.drawable.ic_dog, R.raw.dog),
            Animal("duck", R.drawable.ic_duck, R.raw.duck),
            Animal("elephant", R.drawable.ic_elephant, R.raw.elephant),
            Animal("frog", R.drawable.ic_frog, R.raw.frog),
            Animal("goat", R.drawable.ic_goat, R.raw.goat),
            Animal("horse", R.drawable.ic_horse, R.raw.horse),
            Animal("lion", R.drawable.ic_lion, R.raw.lion),
            Animal("monkey", R.drawable.ic_monkey, R.raw.monkey),
            Animal("owl", R.drawable.ic_owl, R.raw.owl),
            Animal("penguin", R.drawable.ic_penguin, R.raw.penguin),
            Animal("pig", R.drawable.ic_pig, R.raw.pig),
            Animal("rabbit", R.drawable.ic_rabbit, R.raw.rabbit),
            Animal("rhino", R.drawable.ic_rhino, R.raw.rhino),
            Animal("snake", R.drawable.ic_snake, R.raw.snake),
            Animal("tiger", R.drawable.ic_tiger, R.raw.tiger),
            Animal("whale", R.drawable.ic_whale, R.raw.whale),
            Animal("wolf", R.drawable.ic_wolf, R.raw.wolf)
        )
    }
}