// var sortingList = [{
//     id: 1, typeName: "小学一年级"
// }, {
//     id: 2, typeName: "小学二年级"
// }, {
//     id: 3, typeName: "小学三年级"
// }, {
//     id: 4, typeName: "小学四年级"
// }, {
//     id: 5, typeName: "小学五年级"
// }, {
//     id: 6, typeName: "小学六年级"
// }];
var sortingList = [];

var showType = false;//是否显示分类选项列表
var isplaying = false;//音乐是否播放中
var old_type = -1;//old_sorting_id
var music_title = "";
var sortingIndex = -1;
var sortingId = -1;
var subTypeId = -1;
var homeUrl = "https://w.xueyouyou.vip/page";
//var homeUrl = "HTTP://10.210.70.171:8181/page";
var mp3UrlHeader = "https://w.xueyouyou.vip/FM/course/";
var iconUrlHeader = "https://w.xueyouyou.vip/FM/icon/";
//var iconUrlHeader = "HTTP://10.210.70.171:8181/icon/";
var curplay = {};//音乐json
var bgMusic = new Audio("");

// var subtypesList = [
//     {id: '1', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '2', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '3', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '4', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' },
//     { id: '5', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '6', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '7', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '8', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' },
//     { id: '9', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '10', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '11', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '12', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' },
//     { id: '13', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '14', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '15', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '16', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' },
//     { id: '13', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '14', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '15', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '16', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' },{ id: '13', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' },
//     { id: '13', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' },{ id: '13', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }
// ]
var subtypesList =[];

function onload() {
         //加载分类
    //test数据
    //播放

        //bgMusic.src = "http://t1.aixinxi.net/o_1ccn07dld1jfr18fo1akauvp1st0a.mp3";
        //bgMusic.play();

        loadTypes();

        setInterval("playAlrc()",1000);

        //test
    //加载子分类
    // jQuery.ajax({
    //     type: "post",
    //     //url: homeUrl+'/getSubtypesList?key=' + sortingId,
    //     url: homeUrl+'/getSubtypesList?key=' + 119,
    //     async: false,
    //     dataType:"jsonp",  //数据格式设置为jsonp
    //     jsonp:"callback",  //Jquery生成验证参数的名称
    //
    //     success: function(res) {
    //         subtypesList =  res.sortingList;
    //         console.log("sub"+JSON.stringify(res));
    //     }
    // });
}



function loadTypes(){
    //加载分类
    jQuery.ajax({
        type: "post",
        url: homeUrl+"/getSortinglists",
        async: false,
        //dataType: "json", //跨域设置
        dataType:"jsonp",  //数据格式设置为jsonp
        jsonp:"callback",  //Jquery生成验证参数的名称
        success: function(res) {
            console.log(res);
            console.log("success"+JSON.stringify(res));
            sortingList =  res.sortingList;
            console.log(sortingList[0].typeName);

            curplay.id = -1;

            //getPlayStorage();

            //如果有历史缓存
            if(localStorage.getItem("music_id")!=null){ //如果有过播放记录，那么读缓存、加载子分类并播放
                getPlayStorage();
                console.log("music_id"+localStorage.getItem("music_id"));
                //加载子分类
                jQuery.ajax({
                    type: "post",
                    url: homeUrl+'/getSubtypesList?key=' + sortingId,
                    async: false,
                    dataType:"jsonp",  //数据格式设置为jsonp
                    jsonp:"callback",  //Jquery生成验证参数的名称
                    success: function(res) {
                        subtypesList =  res.sortingList;
                        //显示第一个分类下的子分类
                        addSubType();
                        //播放
                        playMusic(curplay.id);
                    }
                });
            }
            else{
                //初始化分类名称
                //console.log("typename"+sortingList[0].typeName);
                var typeValue = document.getElementById('typeValue');
                typeValue.innerHTML = sortingList[0].typeName;
                sortingId = sortingList[0].id;
                old_type = sortingList[0].id;
                addTypes();//加载分类到分类选项列表

                sortingIndex = 0;

                //加载子分类
                jQuery.ajax({
                    type: "post",
                    url: homeUrl+'/getSubtypesList?key=' + sortingList[0].id,
                    dataType:"jsonp",  //数据格式设置为jsonp
                    jsonp:"callback",  //Jquery生成验证参数的名称
                    async: false,
                    success: function(res) {
                        subtypesList =  res.sortingList;
                        console.log(JSON.stringify(res.sortingList));
                        //显示第一个分类下的子分类和子分类名称
                        addSubType();
                        var subTypeValue = document.getElementById('subTypeValue');
                        subTypeValue.innerHTML = subtypesList[0].typeName;
                        subTypeId = subtypesList [0].id;


                        //显示第一个分类下的第一个子分类的第一首歌
                        jQuery.ajax({
                            type: "post",
                            url: homeUrl+'/getFirstSong?id=' + subtypesList [0].id,
                            dataType:"jsonp",  //数据格式设置为jsonp
                            jsonp:"callback",  //Jquery生成验证参数的名称
                            async: false,
                            success: function(res) {
                                console.log(JSON.stringify(res));
                                if (res.songs == undefined) {
                                    var com;
                                    com = confirm("抱歉，暂无课程，请等待哦");
                                    if(com == true){
                                        console.log("true");
                                    }
                                    else{
                                        console.log("false");
                                    }
                                    return;
                                }

                                curplay =  res.songs;
                                if(!res.songs.location){
                                    console.log("mp3链接不存在");
                                }
                                else{
                                    console.log("获取成功");

                                    //播放音乐
                                    //清空一波
                                    // icon.setAttribute("src","image/fm/play_start.png");
                                    console.log("停止所有播放");
                                    //bgMusic.pause();
                                    starttime.innerHTML = "00:00";
                                    endtime.innerHTML = "00:00";
                                    var playprogress = document.getElementById("playrange");
                                    playprogress.value = 0;
                                    playprogress.max = 0;

                                    //播放
                                    bgMusic.src = res.songs.location;
                                    music_title = res.songs.courseName.replace('.mp3', '');
                                    var musicTitle = document.getElementById('musicTitle');
                                    musicTitle.innerHTML = music_title;
                                    bgMusic.play();
                                    bgMusic.pause();
                                    isplaying = true;
                                    play();
                                    curplay = res.songs;
                                    setPlayStorage();
                                }
                            },
                            error:function(res){
                                console.log(res);
                                console.log("获取失败");
                            }
                        });

                    }
                });
            }
        },
        error: function(res){
            console.log("errorhere"+JSON.stringify(res));
        }
    });
}

function addSubType() {  //循环添加子分类
    var st = document.getElementById('st');

    var str = '';

    console.log("subtypesList.length"+subtypesList.length);

    // subtypesList =  [
    // {id: '1', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '2', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '3', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '4', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' },
    // { id: '5', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '6', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '7', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }, { id: '8', typeName: '活力早餐', iconLocation: 'image/fm/music_icon.png' }];

    // subtypesList = [{"id":132,"typeName":"早教音乐","typeLevel":2,"parentType":119,"iconLocation":"1526224849543.jpg","isdisplay":false,"order":4},{"id":132,"typeName":"早教音乐","typeLevel":2,"parentType":119,"iconLocation":"1526224849543.jpg","isdisplay":false,"order":4},{"id":132,"typeName":"早教音乐","typeLevel":2,"parentType":119,"iconLocation":"1526224849543.jpg","isdisplay":false,"order":4},{"id":132,"typeName":"早教音乐","typeLevel":2,"parentType":119,"iconLocation":"1526224849543.jpg","isdisplay":false,"order":4},
    //     {"id":132,"typeName":"早教音乐","typeLevel":2,"parentType":119,"iconLocation":"1526224849543.jpg","isdisplay":false,"order":4},{"id":132,"typeName":"早教音乐","typeLevel":2,"parentType":119,"iconLocation":"1526224849543.jpg","isdisplay":false,"order":4},{"id":132,"typeName":"早教音乐","typeLevel":2,"parentType":119,"iconLocation":"1526224849543.jpg","isdisplay":false,"order":4},{"id":132,"typeName":"早教音乐","typeLevel":2,"parentType":119,"iconLocation":"1526224849543.jpg","isdisplay":false,"order":4}];
    //

    //subtypesList = [{"id":124,"typeName":"学古诗","typeLevel":2,"parentType":119,"iconLocation":"1526223900561.jpg","isdisplay":false,"order":3},{"id":123,"typeName":"就餐音乐","typeLevel":2,"parentType":119,"iconLocation":"1526651239912.jpg","isdisplay":false,"order":2},{"id":124,"typeName":"学古诗","typeLevel":2,"parentType":119,"iconLocation":"1526223900561.jpg","isdisplay":false,"order":3},{"id":124,"typeName":"学古诗","typeLevel":2,"parentType":119,"iconLocation":"1526223900561.jpg","isdisplay":false,"order":3},{"id":124,"typeName":"学古诗","typeLevel":2,"parentType":119,"iconLocation":"1526223900561.jpg","isdisplay":false,"order":3}];
   var c = 0;
    for (var i = 1; i < subtypesList.length+1; i++) {
        var j = i-1;
        if((i - 1)%4 == 0){
            str += '<div class="types">';
            c++;

        }
        str += '<div class="stypes">' +
            '<div data-id='+subtypesList[j].id+' data-index = '+j+' onclick="choiceSub(this)">' +
            '<div class = "subItem">' +
            '<image class = "subimage" src='+iconUrlHeader +subtypesList[j].iconLocation+' />' +
            '</div>' +
            '<div class = "subtext">' +
            '<text>'+subtypesList[j].typeName+'</text>' +
            '</div>' +
            '</div>' +
            '</div>';
        if(i%4 == 0 && i!=0){
            str += '</div>';
        }
    }
    st.innerHTML = str;

    //改变body的margin-bottom
    var body= document.getElementById('body');
    var footer= document.getElementById('footer');

    // var margin = (h / 768) * 100+ 5;
    var w = document.body.clientWidth;
    console.log("w"+w);
    var fh =  w / 800 * 130
    var h = c*125 + fh + 50;

    console.log("h"+h);

    var str2 = 'margin-bottom:'+ h+'px;';
    var str3 = 'height:'+ fh+'px;width: 100%;position: fixed;bottom: 0;background:url("image/footer.jpg");-webkit-background-size:cover;overflow: hidden; ';
    body.setAttribute("style",str2);
    footer.setAttribute("style",str3);

}

function addTypes() {  //循环添加分类
    var st = document.getElementById('showTypes');

    var str = '';

    str+='<div class="ensureitem">' +
        '<text id = "cancel" onclick = "hideTypes()">取消</text>' +
        '<text id = "ok" onclick = "hideTypes()">确定</text>' +
        '</div>'+
        '<div class="scroll" >' +
        '<div class="sorting-item">';

    console.log("sortingList.length"+sortingList.length);

    for (var i = 0; i < sortingList.length; i++) {
        str+='<div class="backgrounddiv" onclick="selectSorting(this)" data-id='+sortingList[i].id+' data-index='+i+'>' +
            '<text decode="emsp">&emsp;'+sortingList[i].typeName+'&emsp;</text>' +
            '</div>';
    }


    st.innerHTML = str;
}



function showTypes(){ //显示分类选项列表
    document.getElementById('showTypes').style.display='block';
    document.getElementById('mask').style.display='block';
}

function selectSorting(e){//选择分类并刷新数据
    var id=e.getAttribute("data-id");
    var index=e.getAttribute("data-index");

    //先恢复先前选中的分类的样式
    if(document.getElementsByClassName('activebackgrounddiv')[0]!=null){
        document.getElementsByClassName('activebackgrounddiv')[0].setAttribute("class","backgrounddiv");
    }

    e.setAttribute("class","activebackgrounddiv");
    sortingId = id;
    sortingIndex = index;
    old_type = id;
    var typeName = document.getElementById('typeValue');
    typeName.innerHTML = sortingList[index].typeName;

    //加载子分类
    jQuery.ajax({
        type: "post",
        url: homeUrl+'/getSubtypesList?key=' + sortingList[index].id,
        async: false,
        dataType:"jsonp",  //数据格式设置为jsonp
        jsonp:"callback",  //Jquery生成验证参数的名称
        success: function(res) {
            console.log(JSON.stringify(res));
            subtypesList =  res.sortingList;
            //显示子分类
            addSubType();
            var subTypeValue = document.getElementById('subTypeValue');
            subTypeValue.innerHTML = subtypesList[0].typeName;
            subTypeId = subtypesList [0].id;

            //显示第一个分类下的第一个子分类的第一首歌
            jQuery.ajax({
                type: "post",
                url: homeUrl+'/getFirstSong?id=' + subtypesList [0].id,
                dataType:"jsonp",  //数据格式设置为jsonp
                jsonp:"callback",  //Jquery生成验证参数的名称
                async: false,
                success: function(res) {
                    console.log(JSON.stringify(res));
                    if (res.songs == undefined) {
                        var com;
                        com = confirm("抱歉，暂无课程，请等待哦");
                        if(com == true){
                            console.log("true");
                        }
                        else{
                            console.log("false");
                        }
                        return;
                    }

                    curplay =  res.songs;
                    if(!res.songs.location){
                        console.log("mp3链接不存在");
                    }
                    else{
                        console.log("获取成功");

                        //播放音乐
                        //清空一波
                        // icon.setAttribute("src","image/fm/play_start.png");
                        console.log("停止当前播放并重新播放");
                        //bgMusic.pause();
                        starttime.innerHTML = "00:00";
                        endtime.innerHTML = "00:00";
                        var playprogress = document.getElementById("playrange");
                        playprogress.value = 0;
                        playprogress.max = 0;

                        //播放
                        bgMusic.src = res.songs.location;
                        music_title = res.songs.courseName.replace('.mp3', '');
                        var musicTitle = document.getElementById('musicTitle');
                        musicTitle.innerHTML = music_title;
                        bgMusic.play();
                        isplaying = true;
                        curplay = res.songs;
                        setPlayStorage();
                    }
                },
                error:function(res){
                    console.log(res);
                    console.log("获取失败");
                }
            });

        }
    });




}

function choiceSub(e){//选择子分类并刷新数据
    //var bgMusic = document.getElementById("bgMusic");
    var starttime = document.getElementById('starttime');
    var icon = document.getElementById('isPlaying');
    var endtime = document.getElementById('endtime');

    //var id=e.getAttribute("data-id");
    var index=e.getAttribute("data-index");
    var id = subtypesList[index].id;
    subTypeId = id;
    var subTypeValue = document.getElementById('subTypeValue');
    subTypeValue.innerHTML = subtypesList[index].typeName;

    // setPlayStorage();

    //根据子分类来获取音频：
    jQuery.ajax({
        type: "post",
        url: homeUrl+'/getFirstSong?id=' + subtypesList [index].id,
        dataType:"jsonp",  //数据格式设置为jsonp
        jsonp:"callback",  //Jquery生成验证参数的名称
        async: false,
        success: function(res) {
            curplay =  res.songs;
            if(!res.songs.location){
                console.log("mp3链接不存在");
            }
            else{
                console.log("获取成功");

                if (res.songs == undefined) {
                    var com;
                    com = confirm("抱歉，暂无课程，请等待哦");
                    if(com == true){
                        console.log("true");
                    }
                    else{
                        console.log("false");
                    }
                    return;
                }

                curplay = res.songs;

                //播放音乐
                //清空一波
                icon.setAttribute("src","image/fm/play_start.png");
                console.log("停止所有播放");
                bgMusic.pause();
                starttime.innerHTML = "00:00";
                endtime.innerHTML = "00:00";
                isplaying = true;
                var playprogress = document.getElementById("playrange");
                playprogress.value = 0;
                playprogress.max = 0;

                //播放
                bgMusic.src = res.songs.location;
                music_title = res.songs.courseName.replace('.mp3', '');
                var musicTitle = document.getElementById('musicTitle');
                musicTitle.innerHTML = music_title;
                bgMusic.play();
                setPlayStorage();
            }
        },
        error:function(res){
            console.log(res);
            console.log("获取失败");
        }
    });
}

function hideTypes(){//隐藏分类选择项列表
    showType = false;
    document.getElementById('showTypes').style.display='none';
    document.getElementById('mask').style.display='none';
}

function playMusic(id){//根据音乐id获取音乐并播放
    //var bgMusic = document.getElementById('bgMusic');
    var starttime = document.getElementById('starttime');
    var icon = document.getElementById('isPlaying');
    var endtime = document.getElementById('endtime');
    //根据id来获取音频：
    jQuery.ajax({
        type: "post",
        url: homeUrl+'/getSong?id=' + id,
        dataType:"jsonp",  //数据格式设置为jsonp
        jsonp:"callback",  //Jquery生成验证参数的名称
        async: false,
        success: function(res) {

            curplay =  res.songs;
            if(!res.songs.location){
                console.log("mp3链接不存在");
            }
            else{
                console.log("获取成功");

                curplay = res.songs;

                //播放音乐
                //清空一波
                icon.setAttribute("src","image/fm/play_start.png");
                console.log("停止所有播放");
                bgMusic.pause();
                starttime.innerHTML = "00:00";
                endtime.innerHTML = "00:00";
                var playprogress = document.getElementById("playrange");
                playprogress.value = 0;
                playprogress.max = 0;

                //播放
                bgMusic.src = res.songs.location;
                music_title = res.songs.courseName.replace('.mp3', '');
                var musicTitle = document.getElementById('musicTitle');
                musicTitle.innerHTML = music_title;
                bgMusic.play();
                isplaying = true;
                setPlayStorage();
            }
        },
        error:function(res){
            console.log(res);
            console.log("获取失败");

            //test数据
            //播放
            //bgMusic.src = "http://t1.aixinxi.net/o_1ccn07dld1jfr18fo1akauvp1st0a.mp3";
            //bgMusic.play();
            //setPlayStorage();

        }
    });
}

function play(){//控制播放or暂停
    //var bgMusic = document.getElementById('bgMusic');
    var icon = document.getElementById('isPlaying');
    if(isplaying){
        bgMusic.pause();
        icon.setAttribute("src","image/fm/play_start.png");
        isplaying = false;
    }
    else{
        bgMusic.play();//播放
        icon.setAttribute("src","image/fm/play_stop.png");
        isplaying = true;
    }
}

function playAlrc(){//获取音频播放状态
    //var bgMusic = document.getElementById('bgMusic');
    var starttime = document.getElementById('starttime');
    var icon = document.getElementById('isPlaying');
    var endtime = document.getElementById('endtime');
    var playprogress = document.getElementById("playrange");
    //var playrange = document.getElementById("playrange");
    //var pslider = document.getElementById("pslider");

    if(bgMusic.ended){//播放完毕
        icon.setAttribute("src","image/fm/play_start.png");
        isplaying = false;
        console.log("播放完毕");

        //重新播放：
        //bgMusic.src = "http://t1.aixinxi.net/o_1ccn07dld1jfr18fo1akauvp1st0a.mp3";
        //bgMusic.play();
        playOther(1);

    }
    else if(bgMusic.paused){//暂停播放
        icon.setAttribute("src","image/fm/play_start.png");
        isplaying = false;
        console.log("暂停播放");
    }
    else{//正常播放
        isplaying = true;
        icon.setAttribute("src","image/fm/play_stop.png");
        console.log("正常播放");
    }

    bgMusic.oncanplay = function(e){
        endtime.innerHTML = secondToDate(bgMusic.duration);
        playrange.max = bgMusic.duration;
    }

    //刷新进度条
    starttime.innerHTML = secondToDate(bgMusic.currentTime);

    if(playprogress.value!=playprogress.max && bgMusic.duration!=undefined){
        if(!bgMusic.paused){
            console.log("bgMusic.duration"+bgMusic.duration);
            playprogress.value++;
            var position = playprogress.value*100 /playprogress.max;
            var str = 'background-size:'+ position +'%  '+ '100%;';
            playprogress.setAttribute("style",str);
        }
        else{
            console.log("playprogress.max"+playprogress.max);
        }
    }
    else{
        playprogress.value = 0;
        playprogress.max = 0;
    }

}

function listenRange(){
    var playrange = document.getElementById("playrange");
    var value = playrange.value
    console.log("value"+value);
    bgMusic.currentTime = value;
}

function secondToDate(result) {//转格式
    var h = Math.floor(result / 3600);
    var m = Math.floor((result / 60 % 60));
    var s = Math.floor((result % 60));
    if(m<10){
        m = "0"+m;
    }
    if(s<10){
        s = "0"+s;
    }
    return result =  m + ":" + s ;
}

function playOther(e){
    //var bgMusic = document.getElementById("bgMusic");
    var starttime = document.getElementById('starttime');
    var icon = document.getElementById('isPlaying');
    var endtime = document.getElementById('endtime');
    var subTypeValue = document.getElementById('subTypeValue');
    var typeName = document.getElementById('typeValue');
    var signal = 0;

    //清空一波
    icon.setAttribute("src","image/fm/play_start.png");
    isplaying = false;
    console.log("停止播放，请求下一首");
    //bgMusic.pause();

    if(e == 1){
        signal = 1;
    }
    else{
        signal = e.getAttribute("data-signal");
    }
    //获取下一首音频id：
    jQuery.ajax({
        type: "post",
        url: homeUrl+'/getOtherSong?id=' + subTypeId+'&music_id=' +curplay.id+'&signal='+signal,
        dataType:"jsonp",  //数据格式设置为jsonp
        jsonp:"callback",  //Jquery生成验证参数的名称
        async: false,
        success: function(res) {
            console.log("成功返回："+JSON.stringify(res));

            //无法获取新音频
            if (res.result == false) {
                if(signal == -1){
                    var com;
                    com = confirm("当前为第一首，请选择下一首哦");
                    if(com == true){
                        console.log("true");
                    }
                    else{
                        console.log("false");
                    }
                    return;
                }
                else{
                    var com;
                    com = confirm("抱歉获取课程失败了哦");
                    if(com == true){
                        console.log("true");
                    }
                    else{
                        console.log("false");
                    }
                    return;
                }
                return;
            }

            curplay.id = res.music_message.music_id;
            sortingId = res.music_message.sorting_id;
            subTypeId = res.music_message.subtype_id;

            //刷新一波界面
            typeName.innerHTML = res.music_message.sorting_name;
            subTypeValue.innerHTML = res.music_message.subtype_name;

            //查找一波sortingIndex
            for (var i = 0; i < sortingList.length; i++) {
                if (sortingId  == sortingList[i].id) {
                    sortingIndex = i;
                }
            }

            if(sortingId!=old_type){//跳分类了
                old_type = sortingId;
                //加载新子分类
                jQuery.ajax({
                    type: "post",
                    url: homeUrl+'/getSubtypesList?key=' + sortingId,
                    async: false,
                    dataType:"jsonp",  //数据格式设置为jsonp
                    jsonp:"callback",  //Jquery生成验证参数的名称
                    success: function(res) {
                        subtypesList =  res.sortingList;
                    }
                });

                //显示子分类
                addSubType();
            }

            starttime.innerHTML = "00:00";
            endtime.innerHTML = "00:00";
            var playprogress = document.getElementById("playrange");
            playprogress.value = 0;
            playprogress.max = 0;

            //播放新音频
            playMusic(res.music_message.music_id);

        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            alert("失败！error");
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        }
    });
}

//切换分类的小箭头
function switchType(e) {
    var signal = 0;
    signal = e.getAttribute("data-signal");

    //取循环sortingIndex
    if(signal == -1){
        if(sortingIndex == 0){
            sortingIndex = sortingList.length - 1;;
        }
        else{
            sortingIndex--;
        }
    }
    else if(signal == 1){
        if (sortingIndex<sortingList.length - 1){
            sortingIndex++;
        }
        else{
            sortingIndex = 0;
        }
    }

    //刷新一波界面
    var typeName = document.getElementById('typeValue');
    typeName.innerHTML = sortingList[sortingIndex].typeName;

    //加载子分类
    jQuery.ajax({
        type: "post",
        url: homeUrl+'/getSubtypesList?key=' + sortingList[sortingIndex].id,
        async: false,
        dataType:"jsonp",  //数据格式设置为jsonp
        jsonp:"callback",  //Jquery生成验证参数的名称
        success: function(res) {
            subtypesList =  res.sortingList;
        }
    });

    //显示子分类
    addSubType();

}

//转发，这个先不写
function onShareAppMessage(){
    console.log("onShareAppMessage");
}

//记录历史缓存
function setPlayStorage(){
    localStorage.setItem("sortingId", sortingId);
    localStorage.setItem("sortingIndex", sortingIndex);
    localStorage.setItem("subTypeId", subTypeId);
    localStorage.setItem("sortingName", sortingList[sortingIndex].typeName);//供前台页面使用
    localStorage.setItem("subTypeName", $("#subTypeValue").val());//供前台页面使用
    if(curplay!={}){
        localStorage.setItem("musicId", curplay.id);
    }
    console.log("sortingId:"+sortingId+"subTypeId:"+subTypeId+"sortingName"+sortingList[sortingIndex].typeName+"subTypeName"+$("#subTypeValue").val()+"music_id"+curplay.id);
}

//取历史缓存
function getPlayStorage(){
    var subTypeValue = document.getElementById('subTypeValue');
    var typeName = document.getElementById('typeValue');

    if(localStorage.getItem("sortingId")!=null){
        console.log("读取sortingId缓存成功");
        sortingId = localStorage.getItem("sortingId");
        old_type = sortingId;
    }
    if(localStorage.getItem("sortingIndex")!=null){
        sortingIndex = localStorage.getItem("sortingIndex");
    }
    if(localStorage.getItem("subTypeId")!=null){
        subTypeId = localStorage.getItem("subTypeId");
    }
    if(localStorage.getItem("sortingName")!=null){
        typeName.innerHTML = localStorage.getItem("sortingName");
    }
    if(localStorage.getItem("subTypeName")!=null){
        subTypeValue.innerHTML = localStorage.getItem("subTypeName");
    }
    if(localStorage.getItem("musicId")!=null){
        curplay.id= localStorage.getItem("subTypeName");
    }
}