/*!
 * jqPagination, a jQuery pagination plugin (obviously)
 * Version: 1.4 (26th July 2013)
 *
 * Copyright (C) 2013 Ben Everard
 *
 * http://beneverard.github.com/jqPagination
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *     
 */(function(e){"use strict";e.jqPagination=function(t,n){var r=this;r.$el=e(t);r.el=t;r.$input=r.$el.find("input");r.$el.data("jqPagination",r);r.init=function(){r.options=e.extend({},e.jqPagination.defaultOptions,n);r.options.max_page===null&&(r.$input.data("max-page")!==undefined?r.options.max_page=r.$input.data("max-page"):r.options.max_page=1);r.$input.data("current-page")!==undefined&&r.isNumber(r.$input.data("current-page"))&&(r.options.current_page=r.$input.data("current-page"));r.$input.removeAttr("readonly");r.updateInput(!0);r.$input.on("focus.jqPagination mouseup.jqPagination",function(t){if(t.type==="focus"){var n=parseInt(r.options.current_page,10);e(this).val(n).select()}if(t.type==="mouseup")return!1});r.$input.on("blur.jqPagination keydown.jqPagination",function(t){var n=e(this),i=parseInt(r.options.current_page,10);if(t.keyCode===27){n.val(i);n.blur()}t.keyCode===13&&n.blur();t.type==="blur"&&r.setPage(n.val())});r.$el.on("click.jqPagination","a",function(t){var n=e(this);if(n.hasClass("disabled"))return!1;if(!t.metaKey&&!t.ctrlKey){t.preventDefault();r.setPage(n.data("action"))}})};r.setPage=function(e,t){if(e===undefined)return r.options.current_page;var n=parseInt(r.options.current_page,10),i=parseInt(r.options.max_page,10);if(isNaN(parseInt(e,10)))switch(e){case"first":e=1;break;case"prev":case"previous":e=n-1;break;case"next":e=n+1;break;case"last":e=i}e=parseInt(e,10);if(isNaN(e)||e<1||e>i){r.setInputValue(n);return!1}r.options.current_page=e;r.$input.data("current-page",e);r.updateInput(t)};r.setMaxPage=function(e,t){if(e===undefined)return r.options.max_page;if(!r.isNumber(e)){console.error("jqPagination: max_page is not a number");return!1}if(e<r.options.current_page){console.error("jqPagination: max_page lower than current_page");return!1}r.options.max_page=e;r.$input.data("max-page",e);r.updateInput(t)};r.updateInput=function(e){var t=parseInt(r.options.current_page,10);r.setInputValue(t);r.setLinks(t);e!==!0&&r.options.paged(t)};r.setInputValue=function(e){var t=r.options.page_string,n=r.options.max_page;t=t.replace("{current_page}",e).replace("{max_page}",n);r.$input.val(t)};r.isNumber=function(e){return!isNaN(parseFloat(e))&&isFinite(e)};r.setLinks=function(e){var t=r.options.link_string,n=parseInt(r.options.current_page,10),i=parseInt(r.options.max_page,10);if(t!==""){var s=n-1;s<1&&(s=1);var o=n+1;o>i&&(o=i);r.$el.find("a.first").attr("href",t.replace("{page_number}","1"));r.$el.find("a.prev, a.previous").attr("href",t.replace("{page_number}",s));r.$el.find("a.next").attr("href",t.replace("{page_number}",o));r.$el.find("a.last").attr("href",t.replace("{page_number}",i))}r.$el.find("a").removeClass("disabled");n===i&&r.$el.find(".next, .last").addClass("disabled");n===1&&r.$el.find(".previous, .first").addClass("disabled")};r.callMethod=function(t,n,i){switch(t.toLowerCase()){case"option":if(i===undefined&&typeof n!="object")return r.options[n];var s={trigger:!0},o=!1;e.isPlainObject(n)&&!i?e.extend(s,n):s[n]=i;var u=s.trigger===!1;s.current_page!==undefined&&(o=r.setPage(s.current_page,u));s.max_page!==undefined&&(o=r.setMaxPage(s.max_page,u));o===!1&&console.error("jqPagination: cannot get / set option "+n);return o;case"destroy":r.$el.off(".jqPagination").find("*").off(".jqPagination");break;default:console.error('jqPagination: method "'+t+'" does not exist');return!1}};r.init()};e.jqPagination.defaultOptions={current_page:1,link_string:"",max_page:null,page_string:"Page {current_page} of {max_page}",paged:function(){}};e.fn.jqPagination=function(){var t=this,n=e(t),r=Array.prototype.slice.call(arguments),i=!1;if(typeof r[0]=="string"){r[2]===undefined?i=n.first().data("jqPagination").callMethod(r[0],r[1]):n.each(function(){i=e(this).data("jqPagination").callMethod(r[0],r[1],r[2])});return i}t.each(function(){new e.jqPagination(this,r[0])})}})(jQuery);if(!console){var console={},func=function(){return!1};console.log=func;console.info=func;console.warn=func;console.error=func};