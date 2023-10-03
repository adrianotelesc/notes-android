package com.adrianotelesc.notes.di

import com.adrianotelesc.notes.data.repository.NoteRepository
import com.adrianotelesc.notes.data.repository.NoteRepositoryImpl
import com.adrianotelesc.notes.ui.screen.notes.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::NotesViewModel)
}

val repoModule = module {
    singleOf<NoteRepository>(::NoteRepositoryImpl)
}

val appModules = viewModelModule + repoModule
