<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <title>申请向导</title>
    <link rel="stylesheet" href="${ctxStatic}/ystep/css/ystep.css">
    <link rel="stylesheet" href="${ctxStatic}/ystep/css/bootstrap.css">
    <style type="text/css">
        /*.ystep-step-undone{*/
        /*width: 85px;*/
        /*}*/
        /*.ystep-lg li {*/
        /*margin-right: 10px;*/
        /*}*/
        body {
            width: 100%;
            height: 100%;
        }

        div {
            margin: 0px;
            padding: 0px;
            width: 100%;
        }
    </style>

</head>

<body style="overflow-x: hidden">
<div class="container">
    <div class="ystep"></div>
    <iframe style="margin-top: 5px" frameborder="0" width="100%"
            src="${ctx}/business/standard/list1" scrolling="no"
            id="myframe" onload="setIframeHeight(this);"></iframe>
</div>
<script src="${ctxStatic}/ystep/js/jquery.min.js"></script>
<script src="${ctxStatic}/ystep/js/bootstrap.min.js"></script>
<script src="${ctxStatic}/ystep/js/ystep.js"></script>

<script>
    window.setInterval("setIframeHeight(document.getElementById('myframe'))", 200);
    function setIframeHeight(iframe) {
        if (iframe) {
            var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
            if (iframeWin.document.body) {
                iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
            }
        }
    }
    window.onload = function () {
        setIframeHeight(document.getElementById('myframe'));
    }
    function SetCwinHeight() {
        var iframeid = document.getElementById("myframe"); //iframe id
        if (document.getElementById) {
            if (iframeid && !window.opera) {
                if (iframeid.contentDocument && iframeid.contentDocument.body.offsetHeight) {
                    iframeid.height = iframeid.contentDocument.body.offsetHeight + 50;
                } else if (iframeid.Document && iframeid.Document.body.scrollHeight) {
                    iframeid.height = iframeid.Document.body.scrollHeight + 50;
                }
            }
        }
    }
    $(".ystep").loadStep({
        steps: ["基本信息填写", "专业技术能力举证", "组织贡献自评", "通用素质自评", "个人评估", "总览"],
        beforeChange: function (nowIndex, nextIndex) {
//            console.log("跳转前校验:" + nowIndex + " " + nextIndex);

            if (nowIndex === 1 && nextIndex === 2) {
                return true;
            } else if (nowIndex === 2 && nextIndex === 3) {
                var flag = document.getElementById('myframe').contentWindow.vl();
                if (!flag) {
                    top.$.jBox.error("有未填写內容，请检查", "系统提示");
                }
                return flag;
            } else if (nowIndex === 3 && nextIndex === 4) {
                var flag = document.getElementById('myframe').contentWindow.validData();
                if (!flag) {
                    top.$.jBox.error("有未填写內容，请检查", "系统提示");
                }
                return flag;
            } else if (nowIndex === 4 && nextIndex === 5) {
                var flag = document.getElementById('myframe').contentWindow.validData();
                if (!flag) {
                    top.$.jBox.error("有未填写內容，请检查", "系统提示");
                }
                return flag;
            } else if (nowIndex === 5 && nextIndex === 6) {
                var flag = document.getElementById('myframe').contentWindow.validData();
                if (!flag) {
                    top.$.jBox.error("请上传个人自评文件！", "系统提示");
                }
                return flag;
            }
            return true;
        },
        afterChange: function (nowIndex, prevIndex) {
//            console.log("跳转后动作:" + nowIndex + " " + prevIndex);

            if (nowIndex < 1 || nowIndex > 6) return;

            /*这里可以搭配其他的页面元素，实现tab页切换的效果*/
//            for (var i = 1; i <= 6; i++) {
//                if (i === nowIndex) {
//                    $("#page" + i).css("display", "block");
//                } else {
//                    $("#page" + i).css("display", "none");
//                }
//            }

            //根据index跳转页面
            if (nowIndex === 2 && prevIndex === 1) {
                //保存基本信息并且跳到下一步
                saveBaseinfo();
                <%--$("#myframe").attr("src", "${ctx}/business/employee/list");--%>
            } else if (nowIndex === 3 && prevIndex === 2) {
                //保存专业技能能力举证并且跳到下一步
                $("#myframe").contents().find("#saveInfo").click();
                $("#myframe").attr("src", "${ctx}/business/orgConSelf/list");
            } else if (nowIndex === 4 && prevIndex === 3) {
                //保存组织贡献自评并且跳到下一步
                $("#myframe").contents().find("#saveOrgInfo").click();
                $("#myframe").attr("src", "${ctx}/business/assess/list");
            } else if (nowIndex === 5 && prevIndex === 4) {
                //保存通用素质自评并且跳到下一步
                $("#myframe").contents().find("#saveQalList").click();
                $("#myframe").attr("src", "${ctx}/business/assess/self");
            } else if (nowIndex === 6 && prevIndex === 5) {
                //跳到总览
//                $("#myframe").contents().find("#upload").click();
                $("#myframe").attr("src", "${ctx}/business/overview/list");
            } else if (nowIndex === 1 && prevIndex === 2) {
                $("#myframe").attr("src", "${ctx}/business/standard/list1");
            } else if (nowIndex === 2 && prevIndex === 3) {
                $("#myframe").attr("src", "${ctx}/business/employee/list");
            } else if (nowIndex === 3 && prevIndex === 4) {
                $("#myframe").attr("src", "${ctx}/business/orgConSelf/list");
            } else if (nowIndex === 4 && prevIndex === 5) {
                $("#myframe").attr("src", "${ctx}/business/assess/list");
            } else if (nowIndex === 5 && prevIndex === 6) {
                $("#myframe").attr("src", "${ctx}/business/assess/self");
            }
        }
    });
</script>

<script>
    function saveBaseinfo() {
        $.ajax({
            url: '${ctx}/business/standard/save',
            type: "POST",
            dataType: 'json',
            data: $("#myframe").contents().find("#infoForm").serialize(),
            success: function (data) {
                if (data.code == 200) {
                    top.$.jBox.success(data.msg, "系统提示");

                    $("#myframe").attr("src", "${ctx}/business/employee/list");
                } else {
                    top.$.jBox.error(data.msg, "系统提示");
                    window.location.reload();
                }
            }
        });
    }

</script>

</body>
</html>