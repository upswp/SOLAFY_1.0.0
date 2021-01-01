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
    Axios.post("group/createApplyGroupSignUp", param)
    .then(success)
    .catch(fail)
}

//일반회원을 그룹에 초대한다
function createGroupInvitation(param, success, fail){}

//모든 그룹 리스트를 받아온다 uid값은 해당 그룹에 가입되어 있는지 확인하기 위해 전달
function selectAllGroup(success, fail){
    Axios.get("group/selectAllGroup/"+ firebaseAuth.currentUser.uid)
    .then(success)
    .catch(fail)
}

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
//그룹의 맴버를 모두 받아온다
function selectGroupMember(param, success, fail) {
    Axios.get("group/selectGroupMember/" + param)
    .then(success)
    .catch(fail)
}

//그룹정보 업데이트
function updateGroup(param, success, fail) {
    console.log(param);
    Axios.put("group/updateGroup", param)
    .then(success)
    .catch(fail)
}
//맴버의 권한 수정
function updatePermissionOfMember(param, success, fail) { }


//가입 신청을 수락했을 때
function updateGroupApplyConfirm(param, success, fail) {
    Axios.put("group/updateGroupApplyConfirm", param)
    .then(success)
    .catch(fail)

}

// TODO : 일반 회원이 그룹 초대를 확인함
function updateGroupInvitationConfirm(param, success, fail){}


//TODO : 그룹을 삭제
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