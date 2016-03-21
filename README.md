# Hegel Bot

HegelBot is the clojure program which I cron to run every 15 minutes to
generate tweets based on trending topics.

HegelBot avoids talking about the same topic twice within a 24-hour
period.

## Usage

This program depends on `char-rnn` to generate the neural network, and
the messages. You will need to set (in `hegel-bot.status`) the location
of your `torch` installed binaries (specifically `th`), and the location
of your trained neural network.

Ostensibly, you could train your own neural network on any philosopher
you want, and transform `hegel-bot` into, say, `plato-bot` or
`wittgenstein-bot`.

You also need to set the information in
`resources/twitter_credentials.edn` to allow `hegel-bot.twitter` to
properly tweet. The comments in `hegel-bot.twitter` should tell you
which fields to specify.

The main method would then pull down the trending topics, and then
generate tweets about the recent trends.

## License

Copyright © 2016 Alex Nelson

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
