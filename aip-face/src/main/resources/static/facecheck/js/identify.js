//访问用户媒体设备的兼容方法
function getUserMedia(constraints, success, error) {
    if (navigator.mediaDevices.getUserMedia) {
        //最新的标准API
        navigator.mediaDevices.getUserMedia(constraints).then(success).catch(error);
    } else if (navigator.webkitGetUserMedia) {
        //webkit核心浏览器
        navigator.webkitGetUserMedia(constraints, success, error)
    } else if (navigator.mozGetUserMedia) {
        //firfox浏览器
        navigator.mozGetUserMedia(constraints, success, error);
    } else if (navigator.getUserMedia) {
        //旧版API
        navigator.getUserMedia(constraints, success, error);
    }
}

var video = document.getElementById('video');
var canvas = document.getElementById('canvas');
var context = canvas.getContext('2d');

function success(stream) {
    //兼容webkit核心浏览器
    var CompatibleURL = window.URL || window.webkitURL;
    //将视频流设置为video元素的源
    console.log(stream);

    //video.src = CompatibleURL.createObjectURL(stream);
    video.srcObject = stream;
    video.play();
}

function error(error) {
    console.log('访问用户媒体设备失败${error.name}, ${error.message}');
}


if (navigator.mediaDevices.getUserMedia || navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia) {
    //调用用户媒体设备, 访问摄像头
    getUserMedia({video: {width: 240, height: 160}}, success, error);
} else {
    alert('不支持访问用户媒体');
}

$(function () {
    identify.init();
});

/**
 * 注册服务事件
 * @type {{init: identify.init, initDate: identify.initDate, initEvent: identify.initEvent}}
 */
var identify = {
    init: function () {
        identify.initDate();
        identify.initEvent();
    },
    initDate: function () {


    },
    initEvent: function () {
        identify.event.capture();


    }
}
identify.event = {

    capture: function () {
        $("#capture").on("click", function () {
            context.drawImage(video, 0, 0, 240, 160);
            identify.fn.identifyUser();
        });
    }


}

identify.fn = {

    identifyUser: function () {
        var canvas = document.getElementById("canvas");
        var dataURL = canvas.toDataURL("image/jpg");
        // document.write(dataURL.toString());

        $.ajax({
            url: '/identifyUser',
            type: 'POST', //GET
            async: true,    //或false,是否异步
            data: {
                imgData: dataURL,

            },
            timeout: 5000,    //超时时间
            dataType: 'json',    //返回的数据格式：json/xml/html/script/jsonp/text
            beforeSend: function (xhr) {
                // console.log(xhr)
                // console.log('发送前')
            },
            success: function (data, textStatus, jqXHR) {
                console.log("[RESULT]")
                console.log(data);

                console.log(textStatus);
                console.log(jqXHR);
                if(data.error_msg){
                    alert(data.error_msg);
                }else{
                    $("#identify-result-uid").html(data.result[0].uid);
                    $("#identify-result-info").html(data.result[0].user_info);
                }



                alert(JSON.stringify(data));

            },
            error: function (xhr, textStatus) {
                console.log('错误')
                console.log(xhr)
                console.log(textStatus)
            },
            complete: function () {
                //console.log('结束')
            }
        });
    }
}


