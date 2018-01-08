/**
 * IP验证
 * @param ip
 * @returns {Boolean}
 */
function isIP(ip){  
    var reSpaceCheck = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;  
    if (reSpaceCheck.test(ip)){  
        ip.match(reSpaceCheck); 
        var result = RegExp.$1<=255&&RegExp.$1>=0&&RegExp.$2<=255&&RegExp.$2>=0&&RegExp.$3<=255&&RegExp.$3>=0&&RegExp.$4<=255&&RegExp.$4>=0;
        return result;
    }else{
    	return false;
    }
}  

/**
 * IP转换成十进制值
 * @param ip
 * @returns {Number}
 */
function ip2num(ip){ 
    var num = 0;
    ip = ip.split(".");
    num = Number(ip[0]) * 256 * 256 * 256 + Number(ip[1]) * 256 * 256 + Number(ip[2]) * 256 + Number(ip[3]);
    num = num >>> 0;
    return num;
}