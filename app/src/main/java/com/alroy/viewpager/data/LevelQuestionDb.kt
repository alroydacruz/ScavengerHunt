package com.alroy.viewpager.data


import com.alroy.viewpager.R
import com.alroy.viewpager.models.QuestionContentModel


object LevelQuestionDb {

    fun getAllQuestions(): ArrayList<QuestionContentModel> {
        val questionsList = ArrayList<QuestionContentModel>()

        ////////////////////////////////            level  1             ///////////////////////////////////////////////////////////////////
        questionsList.add(
            QuestionContentModel(
                0,
                0,
                "next.json",
                "Getting Started",
                "Lets kick things of with something simple. Long ago the hunters used vines for a lot of their daily needs, for clothing, for traps and sometimes for...maths?",
                "https://imgur.com/GkYnV9U",
                image = R.drawable.ic_helpertext,
                correctAnswer = "21",
                thumbnailBgColor = "#FFC107",
                hints = arrayListOf(
                    "Know your vines",
                )
            )
        )

        ////////////////////////////////           level  2   (multiple)        ///////////////////////////////////////////////////////////////////
        questionsList.add(
            QuestionContentModel(
                1,
                0,
                "youtube.json",
                "YouTube & Grill",
                "Let the trail enthuse your senses.",
                "https://youtu.be/mNKkLoATczs",
                correctAnswer = "morsesucks",
                thumbnailBgColor = "#99BBBB"
            )
        )
        questionsList.add(
            QuestionContentModel(
                1,
                1,
                "youtube.json",
                "YouTube & Grill",
                "Let the trail enthuse your senses",
                "https://youtu.be/8XPzZ4YY18c",
                correctAnswer = "camaraderie",
                thumbnailBgColor = "#99BBBB"
            )
        )
        questionsList.add(
            QuestionContentModel(
                1,
                2,
                "youtube.json",
                "YouTube & Grill",
                "Let the trail enthuse your senses",
                "https://youtu.be/QVKJqLZ4sUY",
                correctAnswer = "predilection",
                thumbnailBgColor = "#99BBBB"
            )
        )


        ////////////////////////////////           level  3   (multiple)          ///////////////////////////////////////////////////////////////////
        questionsList.add(
            QuestionContentModel(
                2,
                0,
                "website.json",
                "Rummage Through",
                "Read the signs but more importantly read below the lines!",
                "http://tiny.cc/starthunting",
                correctAnswer = "xurt340z",
                thumbnailBgColor = "#03A9F4",
                hints = arrayListOf(
                    "Upgrade to a laptop, if you haven't already."
                )
            )
        )

        questionsList.add(
            QuestionContentModel(
                2,
                1,
                "website.json",
                "Rummage Through",
                "Read the signs, but more importantly read below the lines !",
                "https://bit.ly/3pOFC6c",
                correctAnswer = "fargo",
                thumbnailBgColor = "#03A9F4",
                hints = arrayListOf(
                    "Upgrade to a laptop, if you haven't already.",
                    "sometimes the hunter needs to drag their prey out from behind obstacles"
                )
            )
        )

        //////////////////////////////////           level  4            ///////////////////////////////////////////////////////////////////
        questionsList.add(
            QuestionContentModel(
                3,
                0,
                "social_media.json",
                "Social Media",
                "Even the Hunter needs to kick back once in a while",
                "https://chat.whatsapp.com/D9DA1nHmYcG17e61eSOQl6",
                correctAnswer = "impure",
                thumbnailBgColor = "#FF96BB",
                image = R.drawable.simp_img,
                hints = arrayListOf(
                  "Only the right person in the WhatsApp group will message you back with the next clue.",
                    "Everything will be in plain sight",
                    "The answer is an English word."
                )
            )
        )


        ////////////////////////////////           level  5             ///////////////////////////////////////////////////////////////////
        questionsList.add(
            QuestionContentModel(
                4,
                0,
                "owl.json",
                "Plot Twist",
                "The poem given below is for the 1st part of this level\n" +
                        "\n" +
                        "\n" +
                        "Plainly visible on the front and back\n" +
                        "Of the red and black\n" +
                        " \n" +
                        "“The one who draws first blood\n" +
                        "adds\n" +
                        "The one who puts Brazil in real, dire trouble\n" +
                        "adds\n" +
                        "The one who gets another one\n" +
                        "adds \n" +
                        "The one who makes it quite astonishing\n" +
                        "adds \n" +
                        "The one who is queued up\n" +
                        "adds \n" +
                        "The one who strikes the penultimate blow\n" +
                        "adds \n" +
                        "The one who hammered it in”\n" +
                        "\n" +
                        "Such was the story of that incredible day\n" +
                        "Fill your answer (in words) in place of { ? } and be on your way.\n" +
                        "\n" +
                        "bit.ly/{ ? }_",
                "https://www.youtube.com/watch?v=a7IMPsyQg6k",
                correctAnswer = "wing dings",
                thumbnailBgColor = "#9A0B00",
                hints = arrayListOf(
                    "The atmosphere in the stadium is electric! You should definitely give it a listen."
                )
            )
        )

        ////////////////////////////////           level  6     (multiple)         ///////////////////////////////////////////////////////////////////
        questionsList.add(
            QuestionContentModel(
                5,
                0,
                "writer.json",
                "What the Beep!",
                "Chase the tunes 'n' shuffle",
                "https://tinyurl.com/y6a93o3h",
                correctAnswer = "babe ruth",
                thumbnailBgColor = "#A1ED5E",
                hints = arrayListOf(
                    "Beep the words into a sentence",
                    "Answer contains 2 words"
                )
            )
        )

        questionsList.add(
            QuestionContentModel(
                5,
                1,
                "writer.json",
                "What the Beep!",
                "Chase the tunes 'n' shuffle",
                "https://bit.ly/38J0ghX",
                correctAnswer = "milton berle",
                thumbnailBgColor = "#A1ED5E",
                hints = arrayListOf(
                    "Answer contains 2 words"
                )
            )
        )

        ////////////////////////////////           level  7             ///////////////////////////////////////////////////////////////////
        questionsList.add(
            QuestionContentModel(
                6,
                0,
                "brain.json",
                "The Finale",
                "Champ, one more round and the PRIZE is yours.",
                "https://drive.google.com/file/d/141NUNh1KvTvfbfs3DImJVv_AAN-lZhgc/view?usp=sharing",
                correctAnswer = "7030145661",
                thumbnailBgColor = "#8E6D85",
                hints = arrayListOf(
                    "Download the image",
                    "Use a laptop/pc for a better experience (not compulsory)",
                    "Flap back for hints we dropped, to complete this level.",
                )
            )
        )

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return questionsList
    }
}