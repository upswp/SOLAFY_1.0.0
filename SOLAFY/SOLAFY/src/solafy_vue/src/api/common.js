import { Notify } from "quasar";

/**
 * 
 * @param {*} c 
 * @param {*} t 
 * @param {*} i 
 * @param {*} m 
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