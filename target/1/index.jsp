<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>学优优超前教育——学优优电台，学优优语文，学优优数学，学优优英语，学优优大学</title>
    <link rel = "stylesheet" href="index.css" media="screen"/>
    <script type="text/javascript" src="http://www.w3dev.cn/rardownload/20130106/20130106170832648.js"></script>
    <script src="jquery/jquery-1.7.1.min.js"></script>
    <script src="jquery/jquery-ui.js"></script>
    <script src="jquery/jquery.form.js"></script>
    <script src="jquery/jquery.tools.min.js"></script>
    <script src="index.js"></script>

</head>
<body  id = "body" onload="onload()">


<div>
    <!-- 用户信息{ -->
    <div class="user">
        <div class="user_info">
            <image id="user_ava" class="user_avator" src="image/share.jpg" />
        </div>

        <!-- 选择分类{ -->
        <div class="select" onclick="showTypes()">
            <image id="select_icon" class="icon-chioce" src="image/fm/music_classify.png" />
            <text id = "typeValue"></text>
            <image id="select_image" class="icon-chioce" src="image/fm/more_enter.png" />
        </div>
        <!-- 选择分类} -->
    </div>
    <!-- 用户信息} -->


    <!-- 列表选项{ -->
    <div class="sorting-list"  id = "showTypes" style = "display: none">
    </div>
    <!-- 列表选项} -->

    <!--播放{-->
    <div id="fm-covermain" style='background:url("image/fm/play_bj.png");-webkit-background-size:cover;overflow: hidden; '>
        <div id="title" class="title">
            <div id="type">
                <image id="type_image" src="image/fm/music_icon.png" style = 'width: 30px;height:30px;'/>
                <text id = "subTypeValue"></text>
            </div>

            <!--<div id = "share_image">-->
            <!--<image src="image/but_share.png" style = 'width: 64px;height:63px;'/>-->
            <!--</div>-->
        </div>
        <div id="musicName" class="musicName">
            <text id="musicTitle"></text>
        </div>
        <div class="fm-fixed" style='position: absolute;left:0px;top:0px;width:669px;height: 400px;'>
            <div id="playing-status" data="{{playtime:playtime,percent:percent,duration:duration}}" >
                <div id = "line" class = "line">
                    <input type="range"  id = "playrange" value="0" max = "100" onchange = "listenRange()">
                    <!--<progress id = "playprogress" class="playprogress" value="0" max="100" style='background: #63a6f9' >-->
                    <!--</progress>-->
                    <!--<img id = "pslider" class = "pslider" src = "image/fm/play_shot.png"></img>-->
                </div>
                <div id = "play_time">
                    <text id = "starttime" class="time" style = 'font-size: 25px;'>00:00</text>
                    <text id="endtime" class="time" style = 'font-size: 25px;'>00:00</text>
                </div>
            </div>
            <div id="fm-action">
                <div  class = "switchleft"  style = 'position: absolute;left:150px;top:400px;color: #fff;overflow: hidden;z-index: 0;width: 30px;height:21px;'  data-signal = "-1" onclick = "playOther(this)">
                    <image src="image/fm/time_left.png"/>
                </div>
                <!--<div bindtap="play" class = "switchmiddle" hidden="{{!playing}}">-->
                <!--<image src="image/fm/play_stop.png" style = 'width: 162px;height:80px;'/>-->
                <!--</div>-->
                <!--<div bindtap="play" class = "switchmiddle" hidden="{{playing}}">-->
                <!--<image src="image/fm/play_start.png" style = 'width: 162px;height:80px;'/>-->
                <!--</div>-->
                <div class = "switchmiddle" style = 'position: absolute;left:260px;top:370px;color: #fff;overflow: hidden;z-index: 0;width: 162px;height:80px;' onclick="play()">
                    <image id = "isPlaying" src="image/fm/play_stop.png" />
                </div>
                <div class = "switchright"  style = 'position: absolute;left:500px;top:400px;color: #fff;overflow: hidden;z-index: 0;width: 30px;height:21px;'  data-signal = "1" onclick = "playOther(this)">
                    <image src="image/fm/time_right.png"/>
                </div>
            </div>


        </div>
    </div>
    <!--播放}-->

    <!--分类{-->
    <div id = "subtypes">
        <!--<div id = "typeleft" data-signal = "-1" onclick = "switchType(this)">-->
        <!--<image src="image/fm/more_left.png" style = 'width: 15px;height:24px;'/>-->
        <!--</div>-->

        <!-- 分类小按钮{ -->
        <div id = "typemiddle">
            <div id = "st">
            </div>

        </div>
        <!-- 分类小按钮} -->
        <!--<div id = "typeright" data-signal = "1"  onclick = "switchType(this)">-->
        <!--<image src="image/fm/more_right.png" style = 'width: 15px;height:24px;'/>-->
        <!--</div>-->
    </div>
    <!--分类}-->


    <div class="mask" id = "mask" style = "display: none">
    </div>

</div>



</body>
<div id = "footer" class = "footer" style='background:url("image/footer.jpg");-webkit-background-size:cover;overflow: hidden; '>
    <!--<div>-->
    <!--<img src="image/footer.jpg">-->
</div>
</html>