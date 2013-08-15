# shower

This is an example of how [mrhyde](https://github.com/dribnet/mrhyde) can be used for interop with the [meteorjs library](http://meteor.com/).


Note: This is just a proof of concept implementation. So far there is just a quick port of the leaderboard example and an oldish [shared grid drawing example](https://github.com/mpnagle/color-magic). This ClojureScript version of the shared grid is also viewable as a [live demo on meteor.com](http://color-magic.shower.meteor.com/). It is known to be a little sluggish - no effort has been made to speed it up yet.

## Usage

```
lein cljsbuild auto
cd color-magic && meteor
open http://localhost:3000/
```

## License

[WTFPL](http://www.wtfpl.net/)
