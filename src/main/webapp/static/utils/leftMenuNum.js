var leftMenuNum = {
    addNum: function (num, numColor) {
        var color = numColor == undefined ? "#ff0000" : numColor;
        var count = num == undefined ? 0 : num;
        var endNumReg = null;
        if (this.isLessIE9()) {
            endNumReg = new RegExp("\<FONT color=(.*)>[0-9]+</FONT>");
        } else {
            endNumReg = new RegExp("\<font color=(.*)>[0-9]+</font>");
        }
        var content = "<font color=\"" + color + "\">" + count + "</font>";
        var appendContent = "[" + content + "]";
        var $jerichotab = $("#jerichotab", window.parent.document);
        var $topTab = $jerichotab.children("div[class='tab_pages']").children("div[class='tabs']").find("li[class='jericho_tabs tab_selected']");
        var topTabId = $topTab.attr("id");
        var menuTabIndex = null;
        var $leftMenu = null;
        var $menu = $("li[class='active']", window.parent.document);
        if (topTabId == null || topTabId == undefined || topTabId == '') {
            $leftMenu = $menu.children('a');
        } else {
            menuTabIndex = topTabId.substring(topTabId.indexOf('_') + 1, topTabId.length + 1);
            $leftMenu = $menu.children('a[jerichotabindex=' + menuTabIndex + ']');
        }
        var menuContent = $leftMenu.html();
        if (!endNumReg.test(menuContent)) {
            $leftMenu.html('');
            $leftMenu.html(menuContent + appendContent);
        } else {
            var newContent = menuContent.replace(endNumReg, content);
            $leftMenu.html(newContent);
        }
    },
    isLessIE9: function () {
        if (!this.isIE()) {
            return false;
        }
        var browser = navigator.appName
        var b_version = navigator.appVersion
        var version = b_version.split(";");
        var trim_Version = version[1].replace(/[ ]/g, "");
        if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE6.0") {
            return true;
        }
        else if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE7.0") {
            return true;
        }
        else if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE8.0") {
            return true;
        }
        else if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE9.0") {
            return false;
        }
        return false;
    },
    isIE: function () {
        if(!+[1,])return true;
        else return false;
    }
}