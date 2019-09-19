package com.cdsen.power.server.user.util;

import com.cdsen.power.server.user.dao.po.PermissionPO;
import com.cdsen.power.server.user.model.vo.PermissionTreeVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HuSen
 * create on 2019/9/19 15:20
 */
public class TreeUtils {

    public static List<PermissionTreeVO> build(List<PermissionPO> poList) {
        return poList
                .stream()
                .collect(Collectors.groupingBy(PermissionPO::getClassification))
                .entrySet()
                .stream()
                .map(entry -> {
                    PermissionTreeVO root = new PermissionTreeVO();
                    root.setId(Integer.MAX_VALUE);
                    root.setName(entry.getKey());
                    root.setChildren(entry.getValue().stream().map(x -> {
                        PermissionTreeVO child = new PermissionTreeVO();
                        child.setId(x.getId());
                        child.setName(x.getDescription());
                        return child;
                    }).collect(Collectors.toList()));
                    return root;
                })
                .collect(Collectors.toList());
    }

    public static List<PermissionTreeVO> buildList(List<PermissionPO> poList) {
        List<PermissionTreeVO> result = new ArrayList<>();
        poList
                .stream()
                .collect(Collectors.groupingBy(PermissionPO::getClassification))
                .forEach((key, value) -> {
                    PermissionTreeVO root = new PermissionTreeVO();
                    root.setId(Integer.MAX_VALUE);
                    root.setName(key);
                    result.add(root);
                    result.addAll(value.stream().map(x -> {
                        PermissionTreeVO child = new PermissionTreeVO();
                        child.setId(x.getId());
                        child.setName(x.getDescription());
                        return child;
                    }).collect(Collectors.toList()));
                });
        return result;
    }
}
