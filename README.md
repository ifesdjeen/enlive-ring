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

Content of `templates/application.html`:

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

```clojure
(html/deftemplate main-template "templates/application.html"
  []
  [:head :title] (html/content "Enlive starter kit"))
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
Content of `templates/header.html`

```html
<!DOCTYPE html>
<html lang="en">
  <body>
    <header>
      <h1>Header placeholder</h1>
      <ul id="navigation">
        <li><a href="#">Placeholder for navigation</a></li>
      </ul>
    </header>
  </body>
</html>
```

```clojure
(html/defsnippet main-template "templates/header.html"
  [:header]
  [heading navigation-elements]
  [:h1] (html/content heading)
  [:ul [:li html/first-of-type]] (html/clone-for [[caption url] navigation-elements]
                                                 [:li :a] (html/content caption)
                                                 [:li :a] (html/set-attr :href url)))
```

## License

Copyright © 2013 Alex Petrov

Distributed under the Eclipse Public License, the same as Clojure.
