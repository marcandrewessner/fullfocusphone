![project banner; title: Just the basics, The best parts of our lives happen outside our phones.](images/project_banner.png)

# Full-Focus Phone

Before we all fell in love with our Smartphones, the 2007 Keynote January 9th, where Steve Jobs presented a novel device that has now become the doors to our worlds, our phones used to be like every other gadgets. Just tools. Easy to lay to the side. We controlled when we picked it up. We controlled when to turn off. The phone (you are propably using to read this) is no doubts magical. But when was the last time you really laid it away. Not upside down on a table, constantly drawing our eyes, awaiting the next buzz, the next signal?

## Our iPhone can do everything, why moving "back"?

I love my iPhone of course. Who doesn't? But with it came the following propblems:

- It pulls our destraction away from "boring" tasks like finishing writing the code documentation or doing our math assignments.
- Even when sitting in a train from Zürich to Lucerne (1h), I cannot really enjoy the ride. Or not enjoy it. It's like not being there at all. There is no room for my Brain to turn off.
- Even when I just want to google the next rest

## But what alternatives do I have?

When trying to search for an alternative to our iPhones that has just the essentials, one of the first options that comes to mind is a nostalgic Nokia. But for our day to day usages it is not enough. Without Apps like Authenticator you are stuck at two factor authentications when logging in to your email or other services. Lets break it down into individual features:

| **Category**          | **iPhone**                         | **This device (focus phone)**                                           | **Nokia (KaiOS)**                                | **Light Phone III**                        |
| --------------------- | ---------------------------------- | ----------------------------------------------------------------------- | ------------------------------------------------ | ------------------------------------------ |
| **WhatsApp**          | ✅                                 | ✅ slow typing (because T9 keyboard)                                    | ❌ (no support anymore, no desktop/web version)  | ❌                                         |
| **Spotify**           | ✅                                 | ✅                                                                      | ❌ very bad app (music stops when screen off...) | ❌                                         |
| **SMS**               | ✅                                 | ✅ slow typing (because T9 keyboard)                                    | ✅ slow typing (because T9 keyboard)             | ✅                                         |
| **Phone Calls**       | ✅                                 | ✅                                                                      | ✅                                               | ✅                                         |
| **Video Calls**       | ✅                                 | ✅ camera at the bottom => weird angle but ok                           | ❌                                               | ❌                                         |
| **Maps / Navigation** | ✅                                 | ✅                                                                      | ❌ very bad app                                  | ✅                                         |
| **Internet Browsing** | ✅                                 | ✅                                                                      | ❌ very bad app                                  | ❌                                         |
| **Cardless Pay**      | ✅                                 | ❌ altough G-Wallet works, I don't trust the CCC certified phone enough | ❌                                               | ❌                                         |
| **Other Tools**       | ✅ e.g. Authenticator, Ticket App  | ✅                                                                      | ❌                                               | ❌                                         |
| **Camera**            | ✅ great as you know (12MP)        | ✅ (front 8MP & back)                                                   | ❌ (horrible camera if any)                      | ✅ (front only)                            |
| **Hotspot**           | ✅                                 | ✅                                                                      | ✅                                               | ✅                                         |
| **Screen**            | best quality, touch, OLED color    | great quality, touch, LCD color                                         | ok quality, no touch, LCD                        | good quality, touch, e-ink (black & white) |
| **Battery**           | min once a day                     | once every two days                                                     | once every 2-3 days                              | once a week                                |
| **Comment**           | comes with temptation/distractions | (see in personal experience)                                            | too many tools missing like authenticator...     | -                                          |

## Project Explained

The depicted phone (DuoQin F22 Pro, made by Xiomi [duoqin.com](https://www.duoqin.com/)) features a TODO-inch LCD touchscreen with a T9 button keyboard. It comes with Android TODO version. The main problem here is, that the stock Android it comes with does not work well with the keyboard and ruins the whole retro, minimal vibe of the beatifully designed device. Furthermore the biggest challenge is the keyboard. Altough the physical keyboard features latin letters printed on them, the keyboard can either be used to enter chinese characters or just numbers. 

Rendering the following problems to be solved:
- Minimalistic and functional homescreen, displaying the apps in a more sutable way and T9 keyboard friendly
- Keyboard driver; creating software that allows the T9 hardware keyboard to be used simmilar to old nokia phones

The homescreen solution comes with the `FullFocusHomeScreen` project and the driver with the `KeyboardDriver` project.

## How to setup this phone

To setup the phone `FullFocusHomeScreen.apk` and `KeyboardDriver.apk` need to be installed on the phone.

Note: as of now, the settings are stored in the android project xml files

- Todo: build apk >> make available to install (show adb commands)
- changing OS (Launcher to set the home screen)
- show how to vonfigure app list, activate / deacrivate sidebar
- changing keyboard to the app because chinese
- set the keyboard from adb, ahow how to change speed values


## Personal experience

I used the DuoQin F22 Pro phone with the configuration (`FullFocusHomeScreen`, `KeyboardDriver`) for eight months as my main phone. At the beginning it was frustrating to not message my friends as quick as I wanted. But I got pretty good within the first week. For concentrating on my studies, it helped primarily for shutting of and being in the moment. One would think it is not as distracting as an iPhone buzzing with push notifications from a bunch of different apps. Yes the notifications where a lot less. Only those from WhatsApp. With a lot of habits still being the same just a different device, the phone was still misplaced on the table instead the backpack. Unexpectedly it really helped in decompressing, in taking breaks. When commuting in a train or waiting for a bus, this phone just didn't do it for entertainment. Noted; this is a good thing. 

After exams I continued using it for my day to day. And it did the job for me: Hotspot for working in the train or bus, WhatsApp, Spotify, Google Calendar and other utility Applications. Just the basics.

## Benefits and Limitations

- ✅ All needed tools work on this (Authenticator, Calendar e.g Google Calendar)
- ✅ Device is unentertaining enough to leave in your pocket
- ❌ Takes time to get used to the keyboard, even then not as fast as iPhone
- ❌ Apps like Slack or Outlook work but are very unhandy to write with
- ❌ Phone is not CE/FCC certified, thus not elegible for the european / US market
