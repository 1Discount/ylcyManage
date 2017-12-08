
/**
 * MOD by ahming. Add startUnitIndex, zeroEndClear, etc.
 *
 * 中文说明: precision 小数点精度 startUnitIndex 传入的"bytes"对应的单位, 在数组里的索引
 * zeroEndClear 是否清除末尾的零(因为使用toFixed可能会在末尾含零)
 * Convert number of "non-negative" bytes into human readable format
 * @param integer bytes     Number of bytes to convert
 * @param integer precision Number of digits after the decimal separator
 * @param integer startUnitIndex [min 0, max 3] Number of index of unit array towards the param "bytes" value
 * @param bool zeroEndClear true to remove the ending zero numbers, against toFixed...
 * @return string
 */
function prettyByteUnitSize(bytes, precision, startUnitIndex, zeroEndClear) {  
    var byteUnits = ['B','KB','MB','GB','TB','PB','EB','ZB','YB']; // index from 0
    var kilobyte = 1024;
    var megabyte = kilobyte * 1024;
    var gigabyte = megabyte * 1024;
    var terabyte = gigabyte * 1024;
    var result;   
    if ((Math.abs(bytes) >= 0) && (Math.abs(bytes) < kilobyte)) {
        result = bytes + byteUnits[startUnitIndex]; 
    } else if ((Math.abs(bytes) >= kilobyte) && (Math.abs(bytes) < megabyte)) {
        if(zeroEndClear)
            result = parseFloat((bytes / kilobyte).toFixed(precision)) + byteUnits[startUnitIndex + 1]; 
        else            
            result = (bytes / kilobyte).toFixed(precision) + byteUnits[startUnitIndex + 1]; 
    } else if ((Math.abs(bytes) >= megabyte) && (Math.abs(bytes) < gigabyte)) {
        if(zeroEndClear)
            result = parseFloat((bytes / megabyte).toFixed(precision)) + byteUnits[startUnitIndex + 2]; 
        else            
            result = (bytes / megabyte).toFixed(precision) + byteUnits[startUnitIndex + 2];
    } else if ((Math.abs(bytes) >= gigabyte) && (Math.abs(bytes) < terabyte)) {
        if(zeroEndClear)
            result = parseFloat((bytes / gigabyte).toFixed(precision)) + byteUnits[startUnitIndex + 3]; 
        else            
            result = (bytes / gigabyte).toFixed(precision) + byteUnits[startUnitIndex + 3]; 
    } else if (Math.abs(bytes) >= terabyte) {
        if(zeroEndClear)
            result = parseFloat((bytes / terabyte).toFixed(precision)) + byteUnits[startUnitIndex + 4]; 
        else            
            result = (bytes / terabyte).toFixed(precision) + byteUnits[startUnitIndex + 4]; 
    } else {
        result = bytes + byteUnits[startUnitIndex];
    }
    return result;
}