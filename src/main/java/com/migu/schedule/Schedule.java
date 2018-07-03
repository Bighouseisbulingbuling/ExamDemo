package com.migu.schedule;


import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

/*
*类名和方法不能修改
 */
public class Schedule {
		
	
	// 挂起队列
	private static List<Task> jq = Collections.synchronizedList(new LinkedList<Task>());

	//   
	private static Map<Integer,List<Task>> tMap = 
			Collections.synchronizedMap(new HashMap<Integer,List<Task>>());
	
	// 初始化
    public int init() {
    	
    	if(null != jq){
    		jq.clear();	
    	}
        if(null != tMap){
        	tMap.clear();	
        }
        return ReturnCodeKeys.E001;
    }

    // 注册节点
    // 注册成功，返回E003:服务节点注册成功。
	// 如果服务节点编号小于等于0, 返回E004:服务节点编号非法。
	// 如果服务节点编号已注册, 返回E005:服务节点已注册。
    public int registerNode(int nodeId) {
        if(nodeId <= 0){
        	return ReturnCodeKeys.E004;
        }
        if(tMap.containsKey(nodeId)){
        	return ReturnCodeKeys.E005;
        }
        tMap.put(nodeId, null);
        return ReturnCodeKeys.E003;
    }

    // 注销节点
    public int unregisterNode(int nodeId) {
    	
    	if(nodeId <= 0){
        	return ReturnCodeKeys.E004;
        }
        if(!tMap.containsKey(nodeId)){
        	return ReturnCodeKeys.E007;
        }
        if(null != tMap.get(nodeId) && tMap.get(nodeId).size() > 0 ){
        	jq.addAll(tMap.get(nodeId));
        }
        tMap.remove(nodeId);
        return ReturnCodeKeys.E006;
    }

    // 添加任务
    public int addTask(int taskId, int consumption) {
    	if(taskId <= 0){
        	return ReturnCodeKeys.E009;
        }
    	if(checkTaskAndRemove(taskId,false)){
    		return ReturnCodeKeys.E010;
    	}
    	jq.add(new Task(taskId,consumption));
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
    	if(taskId <= 0){
        	return ReturnCodeKeys.E009;
        }
    	if(!checkTaskAndRemove(taskId,true)){
    		return ReturnCodeKeys.E012;
    	}
        return ReturnCodeKeys.E011;
    }


    public int scheduleTask(int threshold) {
        
    	if(threshold <= 0){
    		return ReturnCodeKeys.E002;
    	}
    	
        return ReturnCodeKeys.E000;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        // TODO 方法未实现
        return ReturnCodeKeys.E000;
    }

    /**
     * 	检查要添加或者删除的任务是否存在  
     *  remove 是否删除  true为删除
     */
    private boolean checkTaskAndRemove(int taskId, boolean remove){
        boolean isExist = false;
        //检查挂起队列
        for (Iterator<Task> iter = jq.iterator(); iter.hasNext();){
            if(taskId == iter.next().getTaskId()){
                isExist = true;
                if(remove){
                    iter.remove();
                }
            }
        }
        //检查服务节点
        Collection<List<Task>> coll = tMap.values();
        for(List<Task> list : coll){
            if(null != list && list.size() > 0) {
                for(Iterator<Task> iter = list.iterator(); iter.hasNext();) {
                    if(taskId == iter.next().getTaskId()) {
                        isExist = true;
                        if(remove) {
                            iter.remove();
                        }
                    }  
                }
            }
        }
        
        return isExist;
    }
}
