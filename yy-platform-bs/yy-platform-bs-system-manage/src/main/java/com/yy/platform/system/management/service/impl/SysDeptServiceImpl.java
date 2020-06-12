package com.yy.platform.system.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yy.platform.system.management.dao.SysDeptDao;
import com.yy.platform.system.management.entity.SysDept;
import com.yy.platform.system.management.service.SysDeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDept> implements SysDeptService {

    @Override
    public List<SysDept> queryList(Map<String, Object> params) {
        List<SysDept> deptList = this.list(new QueryWrapper<SysDept>().orderByDesc("CREATE_TIME"));

        for (SysDept sysDeptEntity : deptList) {
            SysDept parentDeptEntity = this.getById(sysDeptEntity.getParentId());
            if (parentDeptEntity != null) {
                sysDeptEntity.setParentName(parentDeptEntity.getName());
            }
        }
        return deptList;
    }

    @Override
    public List<String> queryDetpIdList(String parentId) {
        return baseMapper.queryDetpIdList(parentId);
    }

    @Override
    public List<String> getSubDeptIdList(String deptId) {
        // 部门及子部门ID列表
        List<String> deptIdList = new ArrayList<>();

        // 获取子部门ID
        List<String> subIdList = queryDetpIdList(deptId);
        getDeptTreeList(subIdList, deptIdList);

        return deptIdList;
    }

    /**
     * 递归
     */
    private void getDeptTreeList(List<String> subIdList, List<String> deptIdList) {
        for (String deptId : subIdList) {
            List<String> list = queryDetpIdList(deptId);
            if (list.size() > 0) {
                getDeptTreeList(list, deptIdList);
            }

            deptIdList.add(deptId);
        }
    }
}
