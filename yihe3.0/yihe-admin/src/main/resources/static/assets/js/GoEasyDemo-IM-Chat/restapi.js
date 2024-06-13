

//用户数据示例
let users = [];

$.ajax({
	type:"post",
	url:"/instantChat/findAllUser",
	data:{},
	async: false,
	success:function(data){
		 	 console.log("返回",data)
		 	users=data.data
	} 
}); 

//群数据示例
let groups = [
    
];

function RestApi() {

}

RestApi.prototype.findFriends = function (user) {
    var friendList = users.filter(v => v.uuid != user.uuid);
    return friendList;
}

RestApi.prototype.findGroups = function (user) {
    var groupList = groups.filter(v => v.userList.find(id => id == user.uuid));
    return groupList;
}

RestApi.prototype.findUser = function (username, password) {
    var user = null;
    
    
    $.ajax({
	type:"post",
	url:"/instantChat/findDoctor",
	data:{},
	async: false,
	success:function(data){
		 	 console.log("doctor返回",data.data)
		 	user=data.data
		 	 
	} 
}); 
    console.log("doctor返回json",user)
    return user;
}

RestApi.prototype.findGroupMembers = function (groupId) {
    let members = [];
    let group = groups.find(v => v.uuid == groupId);
    users.map(user => {
        if (group.userList.find(v => v == user.uuid)) {
            members.push(user)
        }
    });
    return members;
};

var restApi = new RestApi()