/* 坐标系转换工具类 */
let lngLat = {}
let XPI = 3.14159265358979324 * 3000.0 / 180.0
let PI = 3.1415926535897932384626
let a = 6378245.0
let ee = 0.00669342162296594323

/**
 * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
 * 即 百度 转 谷歌、高德
 * @param lng 经度
 * @param lat 纬度
 * @returns [jd ,wd]
 */
lngLat.bd09togcj02 = function (lng, lat) {
  lng = +lng
  lat = +lat
  var x = lng - 0.0065
  var y = lat - 0.006
  var z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * XPI)
  var theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * XPI)
  var ggLng = z * Math.cos(theta)
  var ggLat = z * Math.sin(theta)
  return [parseFloat(ggLng.toFixed(6)), parseFloat(ggLat.toFixed(6))]
}

/**
 * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
 * 即谷歌、高德 转 百度
 * @param lng 经度
 * @param lat 纬度
 * @returns [jd ,wd]
 */
lngLat.gcj02tobd09 = function (lng, lat) {
  lng = +lng
  lat = +lat
  var z = Math.sqrt(lng * lng + lat * lat) + 0.00002 * Math.sin(lat * XPI)
  var theta = Math.atan2(lat, lng) + 0.000003 * Math.cos(lng * XPI)
  var bdLng = z * Math.cos(theta) + 0.0065
  var bdLat = z * Math.sin(theta) + 0.006
  return [parseFloat(bdLng.toFixed(6)), parseFloat(bdLat.toFixed(6))]
}
/**
 * WGS84转GCj02
 * @param lng 经度
 * @param lat 纬度
 * @returns [jd ,wd]
 */
lngLat.wgs84togcj02 = function (lng, lat) {
  lng = +lng
  lat = +lat
  if (this.outOfChina(lng, lat)) {
    return [lng, lat]
  } else {
    var dlat = this.transformLat(lng - 105.0, lat - 35.0)
    var dlng = this.transformLng(lng - 105.0, lat - 35.0)
    var radlat = lat / 180.0 * PI
    var magic = Math.sin(radlat)
    magic = 1 - ee * magic * magic
    var sqrtmagic = Math.sqrt(magic)
    dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI)
    dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI)
    var mglat = lat + dlat
    var mglng = lng + dlng
    return [parseFloat(mglng.toFixed(6)), parseFloat(mglat.toFixed(6))]
  }
}
/**
 * GCJ02 转换为 WGS84
 * @param lng 经度
 * @param lat 纬度
 * @returns [jd, wd]
 */
lngLat.gcj02towgs84 = function (lng, lat) {
  lng = +lng
  lat = +lat
  if (this.outOfChina(lng, lat)) {
    return [lng, lat]
  } else {
    var dlat = this.transformLat(lng - 105.0, lat - 35.0)
    var dlng = this.transformLng(lng - 105.0, lat - 35.0)
    var radlat = lat / 180.0 * PI
    var magic = Math.sin(radlat)
    magic = 1 - ee * magic * magic
    var sqrtmagic = Math.sqrt(magic)
    dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * PI)
    dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * PI)
    var mglat = lat + dlat
    var mglng = lng + dlng
    return [parseFloat((lng * 2 - mglng).toFixed(6)), parseFloat((lat * 2 - mglat).toFixed(6))]
  }
}

/**
 * 判断范围
 * lng 经度
 * lat 纬度
 */
lngLat.outOfChina = function (lng, lat) {
  lng = +lng
  lat = +lat
  // 纬度3.86~53.55,经度73.66~135.05
  return !(lng > 73.66 && lng < 135.05 && lat > 3.86 && lat < 53.55)
}

/**
 * 转换经度
 * lng 经度
 * lat 纬度
 */
lngLat.transformLng = function (lng, lat) {
  lng = +lng
  lat = +lat
  var ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng))
  ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0
  ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0
  ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0
  return ret
}
/**
 * 转换纬度
 * lng 经度
 * lat 纬度
 */
lngLat.transformLat = function (lng, lat) {
  lng = +lng
  lat = +lat
  var ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng))
  ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0
  ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0
  ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0
  return ret
}
export default lngLat
