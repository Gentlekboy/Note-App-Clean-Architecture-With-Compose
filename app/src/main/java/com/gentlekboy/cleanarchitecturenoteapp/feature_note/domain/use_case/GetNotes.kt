package com.gentlekboy.cleanarchitecturenoteapp.feature_note.domain.use_case

import com.gentlekboy.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.gentlekboy.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.gentlekboy.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.gentlekboy.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Use case class to get a list of notes by [orderType] and [noteOrder]
 */
class GetNotes(private val noteRepository: NoteRepository) {
    operator fun invoke(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)): Flow<List<Note>> {
        return noteRepository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.timeStamp }
                        is NoteOrder.Colour -> notes.sortedBy { it.colour }
                    }
                }
                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.timeStamp }
                        is NoteOrder.Colour -> notes.sortedByDescending { it.colour }
                    }
                }
            }
        }
    }
}