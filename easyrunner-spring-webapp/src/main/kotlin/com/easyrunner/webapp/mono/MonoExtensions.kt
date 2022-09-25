package com.easyrunner.webapp.mono

import reactor.core.publisher.Mono

class MonoExt {
    companion object {
        fun success(): Mono<Void> = Mono.just("Success").then()
    }
}
