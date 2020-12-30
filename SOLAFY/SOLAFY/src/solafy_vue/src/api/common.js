import { Notify } from "quasar";

/**
 * @author YunKihyeon
 * 
 * @param {string} c color
 * @param {string} t textColor
 * @param {string} i icon
 * @param {string} m message
 * 
 * @description quasar notify 메서드
 */
function notify(c,t,i,m){
Notify.create({
    color: c,
    textColor: t,
    icon: i,
    message: m
  });
}

export {notify};