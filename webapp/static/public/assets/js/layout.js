/**
 Core script to handle the entire theme and core functions
 **/
var Layout = function () {

    // Handle sidebar menu
    var handleSidebarMenu = function () {
        // handle ajax links within sidebar menu
        $('.sidebar-menu').on('click', ' li > a.ajaxify', function (e) {
            e.preventDefault();
            //Metronic.scrollTop();

            var menuContainer = $('.main-sidebar ul');
            menuContainer.children('li.active').removeClass('active');
            menuContainer.children('arrow.active').removeClass('active');

            $(this).parents('li').each(function () {
                $(this).addClass('active');
                $(this).children('arrow').addClass('active');
            });
            $(this).parents('li').addClass('active');

            // if (Metronic.getViewPort().width < resBreakpointMd && $('.page-sidebar').hasClass("in")) { // close the menu on mobile view while laoding a page
            //     $('.page-header .responsive-toggler').click();
            // }

            var url = $(this).attr("href");
            if (url && url.toLowerCase() != 'javaScript:;' && url.toLowerCase() != 'javaScript:void(0);' && url.toLowerCase() != '#') {
                // Metronic.startPageLoading();

                var the = $(this);

                console.log(the);

                $.ajax({
                    type: "GET",
                    cache: false,
                    url: url,
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    dataType: "html",
                    async: false,
                    success: function (data) {
                        if (the.parents('li.active').length === 0) {
                            $('.sidebar-menu > li.active > a').click();
                        }

                        initPage(data);

                    },
                    error: function (data) {
                        initPage(data);
                    },
                });
            }
        });

        // handle ajax link within main content
        $('.content-wrapper').on('click', '.ajaxify', function (e) {
            e.preventDefault();

            var url = $(this).attr("href");
            var pageContentBody = $('.content-wrapper .content');

            $.ajax({
                type: "GET",
                cache: false,
                url: url,
                contentType:"application/x-www-form-urlencoded; charset=utf-8",
                dataType: "html",
                async : false,
                success: function (res) {
                    initPage(data);
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    pageContentBody.html('<h4>Could not load the requested content.</h4>');

                }
            });
        });
    };


    return {
        initSidebar: function() {

            handleSidebarMenu(); // handles main menu

        },

        init: function () {
            this.initSidebar();
        }
    };
}();