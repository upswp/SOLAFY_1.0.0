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
    Axios.get("group/checkDuplicateName/" + param)
    .then(success)
    .catch(fail)
}

//일반회원을 그룹에 초대한다.
function createGroupInvitation(param, success, fail){}


function selectAllGroup(param, success, fail){}
function selectGroupByNo(param, success, fail){}
function selectCheckDuplicateName(param, success, fail){}
function selectGroupMember(param, success, fail){}


function updateGroup(param, success, fail){}
function updatePermissionOfMember(param, success, fail){}
function updateGroupApplyConfirm(param, success, fail){}
function updateGroupInvitationConfirm(param, success, fail){}


function deleteGroup(param, success, fail){}
function deleteGroupMember(param, success, fail){}
function deleteGroupMemberself(param, success, fail){}

export { 
    createGroup, createApplyGroupSignUp, createGroupInvitation,
    selectAllGroup, selectGroupByNo, selectCheckDuplicateName, selectGroupMember,
    updateGroup, updatePermissionOfMember, updateGroupApplyConfirm, updateGroupInvitationConfirm,
    deleteGroup,  deleteGroupMember, deleteGroupMemberself};