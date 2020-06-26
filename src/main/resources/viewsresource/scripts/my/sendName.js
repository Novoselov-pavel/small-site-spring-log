
// варианты реализации функций. Экспорт не требуется
/*function sendName(windows,name) {
    window.location.href = window.location.protocol + '//'+ window.location.host+ window.location.pathname+'?name=' + name;
}
*/

const sendName = (window, name) =>{
    window.location.href = window.location.protocol + '//'+ window.location.host+ window.location.pathname+'?name=' + name;
};