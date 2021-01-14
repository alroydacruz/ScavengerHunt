package com.alroy.viewpager.data

import com.alroy.viewpager.models.RuleModel

object RulesList {

    fun getRulesList() :ArrayList<RuleModel>{

        val ruleList = ArrayList<RuleModel>()
        ruleList.add(RuleModel("Instructions", 0))
        ruleList.add(RuleModel("Be mindful of every detail provided to you. ", 1))
        ruleList.add(RuleModel("The internet will be your best friend.", 1))
        ruleList.add(RuleModel("Put in your registered email wherever and whenever required.", 1))
        ruleList.add(RuleModel("You can use any tool in the bag", 1))
        ruleList.add(RuleModel("Once a Hunter completes a level, he/she will no longer be allowed to revisit the level again.", 1))
        ruleList.add(RuleModel("The sevenfold purpose of this hunt is to lead you to a WhatsApp group", 1))

        return ruleList

    }
}