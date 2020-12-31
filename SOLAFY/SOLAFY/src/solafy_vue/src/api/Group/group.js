import { firebaseAuth } from "src/boot/firebase";
const { default: Axios } = require("axios");

// success, fail은 callback method

//그룹을 생성한다
function createGroup(param, success, fail){
    Axios.post( 
        "group/createGroup/" + firebaseAuth.currentUser.uid, param)
        .then(success)
        .catch(fail)
}

//그룹에 가입신청을 한다
function createApplyGroupSignUp(param, success, fail){
    Axios.get("group/createApplyGroupSignUp/" + param)
    .then(success)
    .catch(fail)
}

//일반회원을 그룹에 초대한다
function createGroupInvitation(param, success, fail){

}


function selectAllGroup(param, success, fail){}

//선택한 그룹의 정보를 받아온다
function selectGroupByNo(param, success, fail){
    Axios.get("group/selectGroupByNo/" + param)
    .then(success)
    .catch(fail)

    
}

//그룹명 중복을 확인한다
function selectCheckDuplicateName(param, success, fail){
    Axios.get("group/checkDuplicateName/" + param)
    .then(success)
    .catch(fail)
}
function selectGroupMember(param, success, fail){}


function updateGroup(param, success, fail){}
function updatePermissionOfMember(param, success, fail){}
function updateGroupApplyConfirm(param, success, fail){}
function updateGroupInvitationConfirm(param, success, fail){}


function deleteGroup(param, success, fail){}

//그룹가입 신청 취소
function deleteGroupMember(param, success, fail){
    Axios.delete("group/deleteGroupMember", param)
    .then(success)
    .catch(fail)
}
//그룹 탈퇴
function deleteGroupMemberself(param, success, fail){
    Axios.delete("roup/deleteGroupMemberself", param)
    .then(success)
    .catch(fail)
}

export { 
    createGroup, createApplyGroupSignUp, createGroupInvitation,
    selectAllGroup, selectGroupByNo, selectCheckDuplicateName, selectGroupMember,
    updateGroup, updatePermissionOfMember, updateGroupApplyConfirm, updateGroupInvitationConfirm,
    deleteGroup,  deleteGroupMember, deleteGroupMemberself};