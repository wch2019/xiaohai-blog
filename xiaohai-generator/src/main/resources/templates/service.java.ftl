package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.xiaohai.common.daomain.ReturnPageData;

/**
 *
 * ${table.comment!} 服务类
 *
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {


    /**
     * 添加${table.comment!}
     *
     * @param ${entity?uncap_first} ${table.comment!}
     * @return Integer
     */
    Integer add(${entity} ${entity?uncap_first});

    /**
     * 删除${table.comment!}
     *
     * @param id 主键
     * @return Integer
     */
    Integer delete(Long id);

    /**
     * 修改${table.comment!}
     *
     * @param ${entity?uncap_first} ${table.comment!}
     * @return Integer
     */
    Integer updateData(${entity} ${entity?uncap_first});

    /**
     * id查询数据
     *
     * @param id id
     * @return   Integer
     */
    ${entity} findById(Long id);

    /**
    * 查询${table.comment!}列表数据
    *
    * @param ${entity?uncap_first} ${table.comment!}
    * @return          Response
    */
    ReturnPageData<${entity}> findListByPage(${entity} ${entity?uncap_first});
}
</#if>
