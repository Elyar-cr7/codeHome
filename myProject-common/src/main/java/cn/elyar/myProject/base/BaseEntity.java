package cn.elyar.myProject.base;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

/**
 * @author elyar
 * @date 2020/11/25 14:20
 * @description
 */
public class BaseEntity {
    @TableId(type = IdType.ASSIGN_UUID)
    protected String id; // 主键

    protected String status;// 状态

    protected String creator;// 创建人

    protected LocalDateTime createTime;// 创建时间

    protected String editor;// 修改人

    protected LocalDateTime editTime;// 修改时间

    protected String remark;// 备注

    public void preInsert(String userId) {
        this.createTime = LocalDateTime.now();
        this.creator = userId;
        this.status = "A";
        this.id= IdUtil.fastSimpleUUID();
    }

    public void preUpdate(String userId) {
        this.editTime = LocalDateTime.now();
        this.editor = userId;
    }
}
