package com.tepusoft.modules.sys.dao;

import com.tepusoft.common.persistence.CrudDao;
import com.tepusoft.common.persistence.annotation.MyBatisDao;
import com.tepusoft.modules.sys.entity.Post;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liying on 2017/7/28.
 */
@MyBatisDao
public interface PostDao extends CrudDao<Post> {
    public Post getPost(@Param("id") String id);
    public void updatePost(Post post);

    Post findPostByPostLevelId(@Param("id") String postLevelId);
}
