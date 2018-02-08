const path = require('path');
const webpack = require('webpack');

module.exports = {
    devtool: 'source-map',
    cache: true,

    entry: ['whatwg-fetch', './src/main/resources/static/src/index.tsx'],
    output: {
        path: __dirname + '/src/main/resources/static/',
        filename: './dist/bundle.js'
    },
    resolve: {extensions: ['.js', '.jsx', '.ts', '.tsx', '.js', '.json']},
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                loader: "awesome-typescript-loader"
            },
            {
                test: /\.css$/,
                loader: 'style-loader!css-loader'
            },
            {
                test: /\.(png|jpg|gif|svg|eot|ttf|woff|woff2|JPG)$/,
                loader: 'url-loader',
                options: {
                    limit: 10000
                }
            }
        ]
    },

    externals: {
        "react": "React",
        "react-dom": "ReactDOM"
    },

    devServer: {
        port: 3000,
        contentBase: "./src/main/resources/static/",
        filename: "./dist/bundle.js",
        historyApiFallback: {
          rewrites: { from: '/^\/explore/tags/', to: 'index' }
        }
    }
};