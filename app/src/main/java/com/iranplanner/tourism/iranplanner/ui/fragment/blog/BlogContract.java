package com.iranplanner.tourism.iranplanner.ui.fragment.blog;

import com.iranplanner.tourism.iranplanner.ui.presenter.Presenter;

import entity.PandaMapList;
import entity.ResultBlogFull;
import entity.ResultBlogList;
import entity.ResultPandaMaps;


/**
 * Created by Hoda
 */
public abstract class BlogContract extends Presenter<BlogContract.View> {
    public interface View {

        void showError(String message);

        void showComplete();

        void showProgress();

        void dismissProgress();

        void showBlogList(ResultBlogList resultBlogList);

        void showBlogFull(ResultBlogFull resultBlogFull);


    }

    public abstract void getBlogList(
            String action,
            String offset,
            String cid,
            String androidId);

    public abstract void getBlogFull(
            String action,
            String idBlog,
            String cid,
            String androidId);
}
