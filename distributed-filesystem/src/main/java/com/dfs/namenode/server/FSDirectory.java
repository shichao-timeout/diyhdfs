package com.dfs.namenode.server;

import java.util.ArrayList;
import java.util.List;

public class FSDirectory {

    private InodeDirectory dirTree;

    public FSDirectory(){
        this.dirTree = new InodeDirectory("/");
    }

    public void mkdkr(String path) {
        String[] pathes = path.split("/");
        InodeDirectory parent = dirTree;
        for (String splitedPath : pathes) {
            if(splitedPath.trim().equals("")){
                continue;
            }
            InodeDirectory dir=findDirectory(parent,splitedPath);
            if(dir != null){
                parent = dir;
                continue;
            }
            //如果不包含这个目录结构就创建一下
            InodeDirectory child = new InodeDirectory(splitedPath);
            parent.addChild(child);
        }
    }

    public InodeDirectory findDirectory(InodeDirectory dir,String path){
        if(dir.getChildren().size() == 0){
            return null;
        }
        InodeDirectory result = null;
        for(INode child:dir.getChildren()){
            if(child instanceof InodeDirectory){
                InodeDirectory childDir = (InodeDirectory) child;
                if((childDir.getPath().equals(path))){
                    return childDir;
                }
                result = findDirectory(childDir,path);
                if(result != null){
                    return result;
                }
            }
        }
        return null;
    }

    private interface INode{}

    class InodeFile implements INode{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class InodeDirectory implements INode{
        private String path;
        private List<INode> children;

        public void addChild(INode inode){
            this.children.add(inode);
        }
        public InodeDirectory(String path) {
            this.path = path;
            this.children = new ArrayList<>();
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public List<INode> getChildren() {
            return children;
        }

        public void setChildren(List<INode> children) {
            this.children = children;
        }
    }
}
