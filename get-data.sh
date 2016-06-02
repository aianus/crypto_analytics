#!/bin/bash

aws s3 sync s3://crypto-trade-history/ crypto-trade-history/
aws s3 sync s3://fx-rates/ fx-rates/
