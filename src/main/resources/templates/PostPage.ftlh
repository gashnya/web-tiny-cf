<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <@c.post post=post/>
        <div class="comments">
            <div class="title">Comments (${(post.comments??)?then(post.comments?size, 0)})</div>
            <#if user??>
                <div class="body">
                    <form method="post" action="/post/${post.id}/addComment">
                        <textarea name="text" id="text">${comment.text!}</textarea>
                        <div class="button-field">
                            <input type="submit" value="Add">
                        </div>
                        <@c.error "comment.text"/>
                    </form>
                </div>
            </#if>

            <div class="comments datatable">
                <table>
                    <thead>
                        <tr>
                            <th>User</th>
                            <th>Text</th>
                            <th>Created</th>
                        </tr>
                    </thead>
                    <tbody>
                    <#if post.comments?? && post.comments?size==0>
                        <tr class="noData">
                            <td colspan="3">
                                No comments
                            </td>
                        </tr>
                    <#else>
                        <#if post.comments??>
                            <#list post.comments as comment>
                                <tr>
                                    <td class="userLogin"><a href="#">${comment.user.login}</a></td>
                                    <td class="text">${comment.text}</td>
                                    <td class="creationTime">${comment.creationTime}</td>
                                </tr>
                            </#list>
                        </#if>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
    <#else>
        No such post
    </#if>
</@c.page>
