Minimalist slides for Scittle ClojureScript.

# quickstart

* Clone this repository
* [Start the dev server](#dev) visit <https://localhost:8000> and then edit [`slides.cljs`](./slides.cljs).

# keys

Slide navigation keys:

- Next: RightArrow, DownArrow, PageDown, Spacebar, Enter
- Prev: LeftArrow, UpArrow, PageUp
- First: Home, Escape, Q
- Last: End

# features

- Easy to deploy static HTML.
- Use hiccup Reagent forms to design slides.
- Tiny hackable codebase.

# dev

Start a live-reloading dev server:

```
echo {} > package.json
npm i cljs-josh
npx josh
```

# about

Built at Barcamp London 2024 for [this talk](https://chr15m.github.io/barcamp-whats-the-point-of-lisp/).
