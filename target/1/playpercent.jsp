<template name="playingpercent">
    <div id="playing-status">
        <div id = "line">
            <progress percent="{{percent}}" stroke-width="4" backgroundColor = "#a2a7aa" activeColor = "#ff4892">
                <text id = "play_shot"></text>
            </progress>
        </div>
        <div id = "play_time">
            <text id = "starttime" class="time" style = 'font-size: 25px;'>{{playtime}}</text>
            <text id="endtime" class="time" style = 'font-size: 25px;'>{{duration}}</text>
        </div>
    </div>
</template>