package entity;

/**
 * Created by h.vahidimehr on 28/02/2018.
 */

public class HomeAndBlog {
    public GetHomeResult homeResult;
    public ResultBlogList blogList;

    public GetHomeResult getHomeResult() {
        return homeResult;
    }

    public void setHomeResult(GetHomeResult homeResult) {
        this.homeResult = homeResult;
    }

    public ResultBlogList getBlogList() {
        return blogList;
    }

    public void setBlogList(ResultBlogList blogList) {
        this.blogList = blogList;
    }

    public HomeAndBlog(GetHomeResult homeResult, ResultBlogList blogList) {
        this.homeResult = homeResult;
        this.blogList = blogList;
    }
}
