<#macro menu data>
<div class="col-md-3 left_col">
   <div class="left_col scroll-view">
        <div class="navbar nav_title" style="border: 0;">
           <a href="index.html" class="site_title"><image width="40" src="/images/logo.gif"/> <span><image width="160" src="/images/webmartini.gif"/> </span></a>
        </div>

        <div class="clearfix"></div>

        <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
            <div class="menu_section">
							    <ul class="nav side-menu">
											<@menutree child=data />
							    </ul>
							 </div>

				      <!-- /menu footer buttons -->
            <div class="sidebar-footer hidden-small">
              <a data-toggle="tooltip" data-placement="top" title="Settings">
                <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="FullScreen">
                <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Lock">
                <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
              </a>
              <a data-toggle="tooltip" data-placement="top" title="Logout">
                <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
              </a>
            </div>
       </div>
		</div>
</div>
</#macro>


<#macro menutree child>

		<#list child as cur>

			<li id="menuid_${cur.id}" >

				<a target="mainFrame" href="javascript:void(0)" <#if cur.children?? && cur.children?size gt 0> <#else> onclick="loadframe('${cur.id}','${cur.parentId}','${cur.url}');" </#if> > 
				     <i class="fa ${cur.icon}"></i> 
				     ${cur.name} <#if cur.children?? && cur.children?size gt 0 > <span class="fa fa-chevron-down"></span> </#if>
				</a>

			 <#if cur.children?? && cur.children?size gt 0> <#else> </li> </#if>

			 <#if cur.children?? && cur.children?size gt 0 >
				   <ul class="nav child_menu">
					  <@menutree child=cur.children/>
					  </ul>		     
				 </li>
			  </#if>

		</#list>

</#macro>