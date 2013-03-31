# shower

This is an example of how [strokes](https://github.com/dribnet/strokes) can be used for interop with the [meteorjs library](http://meteor.com/).


Note: This is just a proof of concept implementation. So far there is just a quick port of an existing [shared grid drawing example](https://github.com/mpnagle/color-magic). This ClojureScript version is also viewable as a [live demo on meteor.com](http://color-magic.shower.meteor.com/). It is known to be a little sluggish - no effort has been made to speed it up yet.

## Usage

```
lein cljsbuild auto
cd color-magic && meteor
open http://localhost:3000/
```

## License

[WTFPL](http://www.wtfpl.net/)
