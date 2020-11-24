function generateTree() {
    $.ajax({
        url: path+"/menu/tree.json",
        type: "GET",
        dataType: "JSON",
        success: function (result) {
            if (result.code == 100) {
                var setting = {
                    "view": {
                        "addDiyDom": myAddDiyDom,
                        "addHoverDom": myAddHoverDom,
                        "removeHoverDom": myRemoveHoverDom
                    },
                    "data": {"key": {"url": "maomao"}}
                }

                var zNodes = result.map.root;

                $(document).ready(function () {
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                });
            } else alert("error")
        }

    })
}
function myAddDiyDom(treeId,treeNode){
    var spanId=treeNode.tId+"_ico";
    $("#"+spanId).removeClass().addClass(treeNode.icon);
}
function myAddHoverDom(treeId,treeNode){

    var btnGroupId=treeNode.tId+"_btnGrp";
    if( $("#"+btnGroupId).length>0) return ;

    var anchorId=treeNode.tId+"_a";

    var addBtn = "<a id='"+treeNode.id+"' class='menuAddBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn = "<a id='"+treeNode.id+"' class='menuRemoveBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='删除节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn = "<a id='"+treeNode.id+"' class='menuEditBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' href='#' title='修改节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";

    var btnHTML="";
    var level=treeNode.level;
    if(level==0){
        btnHTML=addBtn;
    }
    if(level==1){
        btnHTML=addBtn+" "+editBtn;
        if(treeNode.children.length==0)
            btnHTML=btnHTML+" "+removeBtn;
    }
    if(level==2){
        btnHTML=editBtn+" "+removeBtn;
    }
    $("#"+anchorId).after("<span id='"+btnGroupId+"'>"+btnHTML+"</span>")
}

function myRemoveHoverDom(treeId,treeNode){
    var btnGroupId=treeNode.tId+"_btnGrp";
    $("#"+btnGroupId).remove();
}