# Enlive-ring

This is a simple and small sample application to illustrate how to use Enlive as your primary templating
system.

# Concepts

## Template

First thing you need to start, is to define your first template. It's done with `deftemplate` function.
`deftemplate` is used as something what you would call layout in some other templating systems. In essence,
it's either a self-contained page (rarely true in bigger applications), or a container for snippets.

```clojure
(require '[net.cgrand.enlive-html :as html])

(html/deftemplate main-template "templates/application.html"
  [])
```

Now, you can start writing selectors and transformations for the given selectors. Let's add a title to the template. Given that your template already has `<head>` and `<title>` tags, let's insert a title.

Content of [templates/application.html](https://github.com/ifesdjeen/enlive-ring/blob/master/src/templates/application.html):

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <title>This is a title placeholder</title>
  </head>
  <body>
  </body>
</html>
```

[Our template](https://github.com/ifesdjeen/enlive-ring/blob/master/src/com/ifesdjeen/enlive_ring/core.clj#L26-L29) in that case would look like:

```clojure
(html/deftemplate main-template "templates/application.html"
  [{:keys [path]}]
  [:head :title] (html/content "Enlive starter kit")
  [:body] (html/append (header path)))
```

Here, `[:head :title]` is a selector, pretty much like a css
selector. If you're coming from jQuery, you can write same selector as
`$("head title")`. `html/content` is a transformation. It puts the
given content into the element specified by your selector.

## Snippet

Snippet is a unit of your page. It may be logical or visual entry, such
as header, footer, page element. Snippet is usually a part of a template,
and may serve as a container for other snippets.

Let's add several snippets. For example, navigation and some
content. For that, let's first define a template for the navigation.
Content of [templates/header.html](https://github.com/ifesdjeen/enlive-ring/blob/master/src/templates/header.html)

```html
<!DOCTYPE html>
<html lang="en">
  <body>
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="brand" href="#">Project name</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li><a href="#">Home</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
  </body>
</html>
```

Now, [header snippet](https://github.com/ifesdjeen/enlive-ring/blob/master/src/com/ifesdjeen/enlive_ring/core.clj#L15-L24):

```clojure
(html/defsnippet header "templates/header.html"
  [:body :div.navbar]
  [current-path]
  [:a.brand] (html/content "Enlive starter kit")
  [:ul.nav [:li html/first-of-type]] (html/clone-for [[caption url] navigation-items]
                                                     [:li] (if (= current-path url)
                                                             (html/set-attr :class "active")
                                                             identity)
                                                     [:li :a] (html/content caption)
                                                     [:li :a] (html/set-attr :href url)))
```

# Running server

In order to start the server, run:

```
lein ring server
```

## License

Copyright © 2013 Alex Petrov

Distributed under the Eclipse Public License, the same as Clojure.
