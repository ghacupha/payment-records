/*
 * Payment Records - Payment records is part of the ERP System
 * Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
const webpack = require('webpack');
const writeFilePlugin = require('write-file-webpack-plugin');
const webpackMerge = require('webpack-merge');
const BrowserSyncPlugin = require('browser-sync-webpack-plugin');
const FriendlyErrorsWebpackPlugin = require('friendly-errors-webpack-plugin');
const SimpleProgressWebpackPlugin = require('simple-progress-webpack-plugin');
const WebpackNotifierPlugin = require('webpack-notifier');
const path = require('path');
const sass = require('sass');

const utils = require('./utils.js');
const commonConfig = require('./webpack.common.js');

const ENV = 'development';

module.exports = (options) => webpackMerge(commonConfig({ env: ENV }), {
    devtool: 'eval-source-map',
    devServer: {
        contentBase: './target/classes/static/',
        proxy: [{
            context: [
                '/api',
                '/services',
                '/management',
                '/swagger-resources',
                '/v2/api-docs',
                '/h2-console',
                '/auth'
            ],
            target: `http${options.tls ? 's' : ''}://localhost:8791`,
            secure: false,
            changeOrigin: options.tls
        },{
            context: [
                '/websocket'
            ],
            target: 'ws://127.0.0.1:8791',
            ws: true
        }],
        stats: options.stats,
        watchOptions: {
            ignored: /node_modules/
        },
        https: options.tls,
        historyApiFallback: true
    },
    entry: {
        global: './src/main/webapp/content/scss/global.scss',
        main: './src/main/webapp/app/app.main'
    },
    output: {
        path: utils.root('target/classes/static/'),
        filename: 'app/[name].bundle.js',
        chunkFilename: 'app/[id].chunk.js'
    },
    module: {
        rules: [{
            test: /\.(j|t)s$/,
            enforce: 'pre',
            loader: 'eslint-loader',
            exclude: /node_modules/
        },
        {
            test: /\.scss$/,
            use: ['to-string-loader', 'css-loader', 'postcss-loader', {
                loader: 'sass-loader',
                options: { implementation: sass }
            }],
            exclude: /(vendor\.scss|global\.scss)/
        },
        {
            test: /(vendor\.scss|global\.scss)/,
            use: ['style-loader', 'css-loader', 'postcss-loader', {
                loader: 'sass-loader',
                options: { implementation: sass }
            }]
        }]
    },
    stats: process.env.JHI_DISABLE_WEBPACK_LOGS ? 'none' : options.stats,
    plugins: [
        process.env.JHI_DISABLE_WEBPACK_LOGS
            ? null
            : new SimpleProgressWebpackPlugin({
                format: options.stats === 'minimal' ? 'compact' : 'expanded'
              }),
        new FriendlyErrorsWebpackPlugin(),
        new BrowserSyncPlugin({
            https: options.tls,
            host: 'localhost',
            port: 9000,
            proxy: {
                target: `http${options.tls ? 's' : ''}://localhost:9060`,
                ws: true,
                proxyOptions: {
                    changeOrigin: false  //pass the Host header to the backend unchanged  https://github.com/Browsersync/browser-sync/issues/430
                }
            },
            socket: {
                clients: {
                    heartbeatTimeout: 60000
                }
            }
            /*
            ,ghostMode: { // uncomment this part to disable BrowserSync ghostMode; https://github.com/jhipster/generator-jhipster/issues/11116
                clicks: false,
                location: false,
                forms: false,
                scroll: false
            } */
        }, {
            reload: false
        }),
        new webpack.ContextReplacementPlugin(
            /angular(\\|\/)core(\\|\/)/,
            path.resolve(__dirname, './src/main/webapp/')
        ),
        new writeFilePlugin(),
        new webpack.WatchIgnorePlugin([
            utils.root('src/test'),
        ]),
        new WebpackNotifierPlugin({
            title: 'JHipster',
            contentImage: path.join(__dirname, 'logo-jhipster.png')
        })
    ].filter(Boolean),
    mode: 'development'
});
